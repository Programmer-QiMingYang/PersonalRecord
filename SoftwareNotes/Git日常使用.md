### Git日常使用
---
![Git](https://img.shields.io/static/v1?label=Git&message=2.22.0-64-bit&color=brightgreen)&nbsp;&nbsp;





#### 1、初始化本地仓库

1. 安装完成之后，打开`Git Bash`命令窗口，设置用户名和邮箱

   ```bash
   git config --global user.name "Your Name"
   git config --global user.email "Your Email"
   
   ```

2. 初始化本地仓库

   ```bash
   mkdir demo	#创建文件夹，你可以不创建，直接在想作为仓库的文件夹即可
   cd demo
   pwd	#显示当前目录
   git init	#通过git init命令把这个目录变成Git可以管理的仓库
   #出现Initialized empty Git repository in ...	#初始化完成
   #新建一个文件，比如我们已经新建了一个README.md文件
   git add README.md	#将文件添加到仓库，如果是将变动的全部添加使用git add .#也可以写多个add
   git commit -m "创建了一个README.md文件"	#使用git commit告诉Git，把文件提交搭配仓库
   git remote add origin git@github.com:xxxxxx/demo.git	#本地仓库关联远程仓库
   git push -u origin master	#推送到远程仓库
   ```

   

#### 2、克隆远程仓库

```bash
git clone 远程仓库地址	#直接克隆远程仓库
git add . 	#将所有改动的文件添加到仓库
git commit -m "提交信息"	#提交到本地仓库
git push -u origin master 	#推送到远程仓库
```

#### 3、生成公钥和私钥

​	由于本地Git仓库和远程仓库（比如GitHub仓库）之间的传输是通过SSH贾母的，所以我们需要一点点的设置：

```bash
ssh-keygen -t rsa -C "Your Email"
#在电脑用户目录下面会有。ssh目录，里面有Id_rsa和Id_rsa.pub两个文件；Id_rsa是私钥，Id_rsa.pub是公钥，将公钥内容复制，在github->Settings->SSH andGPG keys->SSH keys->New SSH keys,title随便写，key就是公钥内容，黏贴过来即可。最后Add SSH key即刻完成。
```

#### 4、参考资料

Git博大精深，不是会几个命令就可以掌握的，这里只是记录了在web开发中经常使用的那点毫末。

参考资料：

progit_v2.1.17

Git教程-廖雪峰

