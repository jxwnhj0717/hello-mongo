### 只在多态的情况下写入类型
1. MongoConfig.mappingMongoConverter() 构造DefaultMongoTypeMapper，去掉SimpleTypeInformationMapper，这个类负责写入类的全限定名
2. 多态对象上加注解@TypeAlias
3. MongoConfig.scanForEntities() 扫描加了@TypeAlias注解的多态对象

参考资料：
+ https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo-template.type-mapping
+ http://athlan.pl/spring-data-mongodb-remove-_class-define-explicitly/

### 枚举转换器
1. 定义写入转换器EnumWithIdToIntegerConverter和读取转换器EnumWithIdConverterFactory
2. MongoConfig.configureConverters()配置转换器 

参考资料：
+ https://stackoverflow.com/questions/5178622/spring-custom-converter-for-all-enums

