# springboot-examples

## 目的
1. 创建基本工程以及示例代码；
2. 架构基础，创建脚手架工程；
3. 对重要代码记录笔记；
4. 深入理解框架的设计思想


## 项目说明

- 1. 项目的结构
- 2. 软件的版本，需要注意的地方
- 3. 常用操作的脚手架，对框架的再次封装，方便自己使用，理解
- 4. 学习框架的编码风格，深入理解框架的设计思想

### 一、springboot-2.2.0-SNAPSHOT-jpa-thymeleaf
框架：springboot-2.2.0-SNAPSHOT,jpa,thymeleaf
数据库：mysql
#### 工程配置结构：
  代码结构简单，直接导入看源码，在此不一一赘述。
#### 常用操作
  JPA的常用操作已经够用了，基本没有必要封装，如果与复杂的业务需求需要自己配置实体之间的依赖的话，建议多多熟悉Hibernate框架
  
#### 注意事项
  相对于全自动映射框架Hibernate来操作很多数据处理的复杂业务，我更倾向于MyBatis半自动映射框架，可以自己编写SQL语句，可控性更强
  自动化的优点是省时，省力，快速，易用。缺点也显而易见，配置不可见，如果对框架各个组件不熟悉的话，出错之后分析困难，不出问题还好，一出问题就不知所措！而且封装太多不利于初学者对框架的学习于理解。
  所以开发时选择框架很重要，可以依据自己对框架的掌握程度，以及团队人员的知识优势来选择。必要时可以进行集体培训。
### 二、springboot-2.0.0.RELEASE +mongodb，springboot-2.2.0-SNAPSHOT+mongodb
框架：springboot-2.0.0.RELEASE,mongo
数据库：mongodb
#### 工程配置结构：
  > 相同点：
    - 1.都是基于springboot, mongoTemplate做的；
    - 2. 都是maven工程，都有一个api,common子模块
  > 不同点：
    - 1. jar包版本不一样，不过可以任意切换；
    - 2. 2.0.0.RELEASE版本的service，dao和启动类是在同一个目录下,2.2.0-SNAPSHOT的service,dao是在common依赖子模块中，这就导致controller要自适配(@Autowired)service,dao 的话，需要在启动类中配置明确的扫描路径，不然controller依赖不到service,dao
#### 常用操作
常用增，删，改，查，以及批量操作的脚手架，可直接用于生产环境
```java
public abstract class MongoCommonDaoImpl implements IMongoCommonDao{

    @Autowired
    private MongoTemplate mongoTemplate;
    
    ……

}
```
只需要继承这个公共类即可获得增，删，改，查，分页查询，批量增加，批量修改等常用操作，可根据自己的需要修改
#### 注意事项
 springboot 的自动装配是默认开启的，启动类运行时，会自上而下扫描 同目录以及其子目录的包中的注解，如果你的maven子模块有相互依赖(比如我的这个示例项目)
 那就必须在启动类中配置注解，注明需要扫描的依赖包的路径，自动会注入失败！
 有个疑问：当指定了固定扫描的包路径时，默认的自动装配是否就失效了？


## 总结
脚手架虽然需要仔细琢磨，测试，但是写好之后，很方便业务接口的开发，而且有利用理解框架的设计方式与思想，建议自己封装一遍属于自己的脚手架框架

不定期更新，敬请期待！
