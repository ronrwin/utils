#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
from subprocess import Popen, PIPE
 
 
def devices():
    '''
    ��ȡ�豸��Ϣ�����ݲ�ͬ״̬��������ֵ
    1. δ�����豸ʱ������ֵΪ 0
    2. �����ӽ�һ̨�豸ʱ������ֵΪ 1
    3. �����Ӷ�̨�豸ʱ������ֵΪ 2
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
    ��ȡBugreport�������з���
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