package ua.com.alevel.databases;

import com.liferay.petra.string.StringUtil;

public class Database {
    public Database() {
        System.out.println("This is a new Database object");
    }

    public void run() {
        System.out.println("Split word 'Database' to array by delimiter 't':  '"+StringUtil.split("Database", 't'));

    }
}
