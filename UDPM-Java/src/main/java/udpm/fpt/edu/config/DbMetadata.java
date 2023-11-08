/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.edu.config;

public final class DbMetadata {
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123";
    private static final String SERVER = "localhost";
    private static final String PORT = "1433";
    private static final String DATABASE_NAME = "JPA";
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
}