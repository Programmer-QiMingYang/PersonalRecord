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
git clone 远程仓库地址	#直接克隆远程仓库，是克隆所有的历史版本，也可以将地址协议改成git协议面对某些失败的克隆。
git add . 	#将所有改动的文件添加到仓库
git commit -m "提交信息"	#提交到本地仓库
git push -u origin master 	#推送到远程仓库
git clone -b develop 地址 #从develop分支下克隆项目
git clone 地址 -depth 1 #克隆最近一次的commit，适用于项目比较大，我们克隆只需要项目，不需要以往的历史版本，depth 1代表克隆深度
```

#### 3、生成公钥和私钥

​	由于本地Git仓库和远程仓库（比如GitHub仓库）之间的传输是通过SSH贾母的，所以我们需要一点点的设置：

```bash
ssh-keygen -t rsa -C "Your Email"
#在电脑用户目录下面会有。ssh目录，里面有Id_rsa和Id_rsa.pub两个文件；Id_rsa是私钥，Id_rsa.pub是公钥，将公钥内容复制，在github->Settings->SSH andGPG keys->SSH keys->New SSH keys,title随便写，key就是公钥内容，黏贴过来即可。最后Add SSH key即刻完成。
```

#### 4、常用命令

```bash
git log #查看版本信息
git reset --hard 版本号 #版本回退，这个回退不会保留回退之前的版本
git push -f #提交更改，一般在使用完reset之后会使用这个命令

git revert #git revert是用于“反做”某一个版本，以达到撤销该版本的修改的目的。比如，我们commit了三个版本（版本一、版本二、 版本三），突然发现版本二不行（如：有bug），想要撤销版本二，但又不想影响撤销版本三的提交，就可以用 git revert 命令来反做版本二，生成新的版本四，这个版本四里会保留版本三的东西，但撤销了版本二的东西。

```

参考：

1. [Git恢复之前版本的两种方法reset、revert（图文详解）](https://blog.csdn.net/yxlshk/article/details/79944535)

#### 5、参考资料

Git博大精深，不是会几个命令就可以掌握的，这里只是记录了在web开发中经常使用的那点毫末。

参考资料：

progit_v2.1.17

Git教程-廖雪峰

