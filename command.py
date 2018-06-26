#!/usr/bin/python       
# coding=utf-8
import zipfile
import shutil
import os
import shlex
import subprocess
import time
import re
#获取当前路径
currentDir = os.getcwd()
print("-->当前目录%s" % currentDir) 
exist = os.path.exists("command.config")
if exist==False:
	print("--->command.config none")
#Open file
channel_file = 'command.config'
f = open(channel_file)
commandList=[]
for line in f:
	array = line.split(";")
	project_title = array[0]
	commandList.append(array[1])
	print("====>"+project_title) 			
f.close()
str = input("请输入编号：");
command=commandList[str]

if("|" in command):
	for commandOne in command.split("#"):
		print("-->执行命令：%s" % commandOne)
		os.system(commandOne)
else :
	print("-->执行命令：%s" % command)
os.system(command)
