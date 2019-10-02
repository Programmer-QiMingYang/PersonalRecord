### STS（Eclipse）常用配置和使用

-------
![sts](https://img.shields.io/static/v1?label=STS&message=4.3.2.RELEASE&color=brightgreen)&nbsp;&nbsp;![Maven](https://img.shields.io/static/v1?label=Maven&message=3.6.0&color=brightgreen)&nbsp;&nbsp;![Git](https://img.shields.io/static/v1?label=Git&message=2.22.0-64-bit&color=brightgreen)

<nav>
    <a href="#一基础设置">一、基础设置</a><br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#1编码">1、编码</a><br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#2代码自动提示">2、代码自动提示</a><br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#3高亮类中同一引用的使用位置">3、高亮类中同一引用的使用位置</a><br/>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#4debug自动到断点位置">4、debug自动到断点位置</a><br/>
    <a href="#二maven配置">二、maven配置</a><br/>
    <a href="#三常用快捷键">三、常用快捷键</a><br/>
    <a href="#四集成Git">四、集成Git</a><br/>
</nav>



### 一、基础设置

#### 1、编码

##### 1.1 workspace编码

Window -> Preferences -> General -> Workspace -> Text file encoding -> Other：UTF-8

##### 1.2 Jsp编码

1. Window -> Preferences -> Web -> JSP Files -> Text file encoding-> Other：UTF-8
2. jsp文件设置之后还需要到下面位置：Window -> Preferences -> General -> Content Types->Text，将jsp文件再次设置为UTF-8

##### 1.3 其它文件编码（比如properties）

Window -> Preferences -> General -> Content Types->Text，将需要设置的文件设置编码, 最好统一设为UTF-8。

#### 2、代码自动提示

1. Window -> Preferences -> Java -> Editor -> Content Assist -> Auto Activation
2. Auto activation triggers for java 默认是点（.），在点后面添加小写a-z和大写A-Z。
3. Auto activation delay(ms) 延迟默认是0，修改为100-200或者自己觉得可以的数值。

#### 3、高亮类中同一引用的使用位置

Window -> Perferences -> General - > Editors -> TextEditors -> Annotations，选择"Occurrences",勾引"Text as" -> "Highlighted"(块高亮)或"Squiggles"(虚线)，默认是块高亮

#### 4、debug自动到断点位置

windows->Preferences->Java->debug->Suspend Exception，去掉Suspend exception on uncaught exceptions勾选

### 二、maven配置

1. windows->Preferences->Maven->User Settings，在User Settings或者Global Settings选择Browse...选择自己的maven的setting.xml文件。点击Update Settings会自动更新下面的Local Repository(From merged user and global settings)的路径。
2. windows->Show View->Other，输入maven，选择Maven Repositories，点击open。最下面的Views会有Maven Repositories一栏，并有相应的信息。

### 三、常用快捷键

| 快捷键组合   | 功能描述         | 快捷键组合   | 功能描述                 |
| ------------ | ---------------- | ------------ | ------------------------ |
| ctrl+n       | 新建（文件）     | alt+shift+n  | 新建项目                 |
| alt+shift+s  | 唤醒构造、重写等 | alt+shift+m  | 抽取方法                 |
| ctrl+m       | 屏幕独占         | ctrl+e       | 选择文件                 |
| ctrl+h       | 全局搜索         | ctrl+o       | 显示方法                 |
| ctrl+f       | 搜索替换         | ctrl+f7      | 视图切换                 |
| alt+shift+r  | 统一修改         | ctrl+shift+g | 显示调用某个方法的所有类 |
| ctrl+2，L    | 补全左边         | f2           | 修改文件名               |
| ctrl+shift+o | 补全、删除包     |              |                          |
|              |                  |              |                          |

​		

### 四、集成Git

##### 1 STS（Eclipse）的Git插件安装与配置

新版本的Eclipse已经自带Git，不用安装，可以**忽略这一步**；老版本的Eclipse安装Git：help->Install new software,输入git地址。配置Git插件window->Preferences->Team->Git->Configuration->User Settings->Add Entry...，填写email和name即可，这就相当与git的全局设置用户名和邮箱的命令。以后提交代码的时候，Eclipse会自动提取这些信息，和代码一起发送到Git仓库。

##### 2 从Git云端下载项目到本地

Window->Show View->Other...->Git->Git Repositories->Open。这个时候在view视图就可以看到Git Repositories信息，三个选项，我选择第二个Clone a Git repository

![](STS%E5%B8%B8%E7%94%A8%E9%85%8D%E7%BD%AE.assets/eclipse%E4%B8%ADgit%E5%85%8B%E9%9A%86%E8%BF%9C%E7%A8%8B%E4%BB%93%E5%BA%93.png)

**将克隆的地址复制到URI，下面两项以及Connection信息自动补全**，剩下Authentication里面的用户名和密码；上面的用户名和密码，我觉得是自己用于开发的用户名和密码。因为会弹出连接仓库的用户和密码。接下来选择分支，如果你只有一个就是master分支，真实项目都是不断迭代，实际会有多个分支。选择next，配置本地项目存放路径，finish即可完成。然后Import existing projects导入项目进入工作空间。

##### 3 提交代码到Git云端

1. 修改代码，或者新建文件
2. team->commit
3. team->pull
4. 上传到远程仓库
   1. 简易上传到云端Git仓库：team->push Branch master->next->最后finish即可
   2. 上传分支：右键项目->team->Remot->Push->填写信息，最后finish

##### 4 开发常用小技巧

1. 恢复原装代码：Replace with->HEAD Revision
2. 查看提交记录：team->Show in History(命令行：git log)
3. 更新项目远程仓库:Team->Pull(命令行：git pull ”仓库地址“)
4. 假如克隆仓库比较大，git克隆就会很慢，其实很多时候我们不需要记录，只是需要这个仓库最后一次完整的内容：`git clone --depth 1 项目地址`