package ua.com.alevel.databases;

import com.liferay.petra.string.StringUtil;

public class Database {
    public Database() {
        System.out.println("This is a new database");
    }

    public void run() {
        System.out.print("Split word 'Database' to array by delimiter 't':  ");
       System.out.println(StringUtil.split("Database", 't'));
    }
}
