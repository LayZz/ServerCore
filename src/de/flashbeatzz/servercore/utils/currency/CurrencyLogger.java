package de.flashbeatzz.servercore.utils.currency;

import de.flashbeatzz.servercore.utils.MySQL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CurrencyLogger {

    private static String getDateTimeString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    static boolean log(UUID user_id, int amount, String description, String server_name, String class_package, int oldAmount, int newAmount) {
        String sql = "INSERT INTO `currency_logs` (`id`, `user_id`, `amount`, `time`, `description`, `server_name`, `class_package`, `old_amount`, `new_amount`";
        sql += ") VALUES (";
        sql += "NULL, '" + user_id.toString() + "', '" + amount + "', '" + getDateTimeString() + "', '" + description + "', '" + server_name + "', '" + class_package + "', '" + oldAmount + "', '" + newAmount + "')";
        return MySQL.execute(sql);
    }

}
