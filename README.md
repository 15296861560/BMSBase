## SpringBoot

##git创建新的存储库
 ##步骤
 - git init
 - git add README.md
 - git commit -m "first commit"
 - git remote add origin 库地址
 - git push -u origin master

##部署
 - Git
 - JDK
 - Maven
 - MySQL
 ##步骤
 - yum update
 - yum install git
 - mkdir App
 - cd App
 - git clone https://github.com/15296861560/SBBase.git
 - yum install maven
 - mvn -v
 - mvn compile package
 - more src/main/resources/application.properties
 - cp src/main/resources/application.properties src/main/resources/application-production.properties
 - vim src/main/resources/application-production.properties
 - mvn package
 - java -jar -Dspring.profile.active=production target/demo-0.0.1-SNAPSHOT.jar
 - ps -aux | grep-java
 - git pull

## 资料
[Spring 文档](https://spring.io/guides)

[Spring 外部文档](https://spring.io/guides/gs/serving-web-content/)

[GitHub deploy key文档](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)

[BootStrap文档](https://v3.bootcss.com/getting-started/)

[菜鸟教程](https://www.runoob.com/)

[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html)

[图书分类](https://baike.baidu.com/item/中国图书馆图书分类法/1919634?fr=aladdin#2)

[上传图片](https://www.jianshu.com/p/c101b38adade)

[榛子云短信](http://smsow.zhenzikj.com/doc/java_sdk_doc.html)

[SweetAlert](https://sweetalert2.github.io)

[Echarts](https://www.echartsjs.com/zh/index.html)



##工具
[Git](https://git-scm.com/download)

[lombok](https://projectlombok.org/)

##数据库脚本



