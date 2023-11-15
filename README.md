# Dự án UDPM-Java
![Open with kali linux](https://prodwewpstorageaccount.s3.eu-central-1.amazonaws.com/wp-content/uploads/sites/4/2017/07/09061156/Running-Kali-Linux-on-Client-Hyper-V841x281.jpg)

Facebook: [Nong Hoang Vu](https://www.facebook.com/NongHoangVu04)

## Library
- TimingFramework-0.55
- Webcam
- datechooser-swing-1.4.1
- miglayout-4.0

## Maven Dependencies
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
        <version>2.5.0</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
        <version>2.5.0</version>
    </dependency>
    <dependency>
        <groupId>com.microsoft.sqlserver</groupId>
        <artifactId>mssql-jdbc</artifactId>
        <version>10.2.1.jre8</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.30</version>
        <type>jar</type>
    </dependency>
    <dependency>
        <groupId>miglayout-4.0</groupId>
        <artifactId>miglayout-4.0</artifactId>
        <version>1.1</version>
    </dependency>
    <dependency>
        <groupId>mssql-jdbc-12.2.2.jre12Pro</groupId>
        <artifactId>mssql-jdbc-12.2.2.jre12Pro</artifactId>
        <version>1.1</version>
    </dependency>
    <dependency>
        <groupId>TimingFramework-0.55</groupId>
        <artifactId>TimingFramework-0.55</artifactId>
        <version>1.1</version>
    </dependency>
    <dependency>
        <groupId>org.netbeans.external</groupId>
        <artifactId>AbsoluteLayout</artifactId>
        <version>RELEASE190</version>
    </dependency>
    <dependency>
        <groupId>Webcam</groupId>
        <artifactId>Webcam</artifactId>
        <version>1.1</version>
    </dependency>
    <dependency>
        <groupId>datechooser-swing-1.4.1</groupId>
        <artifactId>datechooser-swing-1.4.1</artifactId>
        <version>1</version>
    </dependency>
</dependencies>

```java
public final class DbMetadata {
private static final String USERNAME = "sa";
private static final String PASSWORD = "123";
private static final String SERVER = "localhost";
private static final String PORT = "1433";
private static final String DATABASE_NAME = "THTrueMilk2";
private static final boolean USING_SSL = true;
private static String CONNECT_STRING;
static {
    StringBuilder connectStringBuilder = new StringBuilder();
    connectStringBuilder.append("jdbc:sqlserver://")
            .append(SERVER).append(":").append(PORT).append(";")
            .append("databaseName=").append(DATABASE_NAME).append(";")
            .append("user=").append(USERNAME).append(";")
            .append("password=").append(PASSWORD).append(";");
    if (USING_SSL) {
        connectStringBuilder.append("encrypt=true;trustServerCertificate=true;");
    }
    CONNECT_STRING = connectStringBuilder.toString();
}

public static String getConnectString() {
    System.out.println(CONNECT_STRING);
    return CONNECT_STRING;
}
