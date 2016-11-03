#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
from subprocess import Popen, PIPE
 
 
def devices():
    '''
    获取设备信息，根据不同状态给出返回值
    1. 未连接设备时，返回值为 0
    2. 已连接仅一台设备时，返回值为 1
    3. 已连接多台设备时，返回值为 2
    '''
    resp = Popen(
        'adb devices', shell=True, stdout=PIPE, stderr=PIPE).stdout.readlines()
    cmd = []
    for i in resp:
        cmd.append(i.strip('\r\n'))
 
    if cmd[-2] == cmd[0]:
        print ('...... Devices not fond ......')
        return 0
    elif len(cmd) > 3:
        print ('...... Fond %s devices ......' %
               (len(cmd) - 2))
        return 2
    else:
        print ('...... Device is fond ......')
        return 1
 
 
def analysis_bugreport():
    '''
    获取Bugreport，并进行分析
    '''
    print 'getting bugreport......'
    os.system('adb shell bugreport > %s\\bugreport.log' % os.getcwd())
    print 'Got it.'
    # Bugreport
    os.system('java -jar chkbugreport.jar bugreport.log')
    print 'Analysis complete.'
 
if __name__ == '__main__':
    if devices() == 1:
        analysis_bugreport()