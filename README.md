# Firefly-cloud 萤火虫开发平台
[![](https://img.shields.io/badge/Version-1.1-brightgreen)](http://10.8.132.108)

## 后端技术架构
- 基础框架：Spring Boot 2.6.8
- 持久层框架：Mybatis
- 安全框架：Spring-security
- 数据库连接池：阿里巴巴 Druid
- 缓存框架：redis
- 日志打印：logback
- 其他：fastjson2，jwt，poi，Swagger-ui等。
## 开发环境
- 语言：Java 1.8
- IDE：Eclipse 或 IDEA
- 依赖管理：Maven
- 数据库：MySQL & Oracle & 达梦 
- 缓存：Redis
## 技术文档
- 基础框架演示 ：  [http://10.8.132.108](http://10.8.132.108)
- 基础框架服务接口 ：  [http://10.8.132.108:8080/swagger-ui/index.html](http://10.8.132.108:8080/swagger-ui/index.html)
- 在线开发指导文档：  [http://10.10.10.2:8090/pages/viewpage.action?pageId=60784692](http://10.10.10.2:8090/pages/viewpage.action?pageId=60784692)
## 本地运行部署
- 执行db目录下的`demo.sql`脚本，生成样例表和初始化数据
- 修改`src/resources`目录下的配置文件`application.yml`
> 主要修改以下配置项：
     1、数据源配置信息（`spring.datasource.master.`),根据自身数据库信息进行修改
     2、Redis配置信息(`spring.redis.`),请确保项目的Redis配置信息和萤火虫权限管理平台的Redis配置信息一致(注：要保持一致主要为业务接口的token认证)
     3、mybatis的搜索指定包别名信息（`mybatis.typeAliasesPackage`）,指定包名和项目的包路径一致
     4、萤火虫系统管理服务配置信息(`firefly.service.prefixUrl`),配置为权限管理系统后台微服务地址
- 修改Maven的settings.xml文件，确保Maven配置文件指向公司私服仓库libs-virtual-common
   ```
   <server>
		<id>devRepo</id> 
		<username>dev</username>
        <password>Eastcom#21</password>    
    </server>
    <repository>
        <id>devRepo</id>
        <name>Develope virtual repo</name>
        <url>http://10.8.132.224:8081/artifactory/libs-virtual-common/</url>
    </repository>
   ```
- 在项目的根目录下执行`mvn package`命令，进行编译打包
- 在命令控制台下进入target目录，执行`java -jar xxxxx.jar`命令，启动应用
- 进入Swagger界面，浏览器中打开http://localhost:8080/swagger-ui/index.html
- 通过从萤火虫权限管理平台拿到Swagger中接口调用用到的Token
> 登录萤火虫权限管理平台后，按F12键，然后打开任意一个权限管理平台的功能，从页面的接口调用信息的标头中获取token
- 从Swagger的UI中测试接口，如`/demo/config/list`

## 开发技巧
- [分页实现](http://10.10.10.2:8090/pages/viewpage.action?pageId=60784712)
- [权限注解](http://10.10.10.2:8090/pages/viewpage.action?pageId=60784717)
- [参数验证](http://10.10.10.2:8090/pages/viewpage.action?pageId=60784728)
- [日志记录](http://10.10.10.2:8090/pages/viewpage.action?pageId=60784730)
- [接口描述](http://10.10.10.2:8090/pages/viewpage.action?pageId=60784736)
- 从token中获取用户ID或用户名，调用firefly-cloud-common-core中的JwtUtils类相关方法
