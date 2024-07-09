package com.example.intorn

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(private val context: Context):
            SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "mydatabase.db"

        private const val CREATE_GROUP_TABLE = """
            CREATE TABLE groupp (
                ID INTEGER PRIMARY KEY,
                Name TEXT NOT NULL
            )
        """

        private const val CREATE_DEPARTMENT_TABLE = """
            CREATE TABLE department (
                ID INTEGER PRIMARY KEY,
                GroupID INTEGER,
                Name TEXT NOT NULL,
                FOREIGN KEY (GroupID) REFERENCES groupp(ID)
            )
        """

        private const val CREATE_PRODUCTS_TABLE = """
            CREATE TABLE products (
                ID INTEGER PRIMARY KEY,
                DepartmentID INTEGER,
                ProductNumber TEXT NOT NULL,
                Name TEXT NOT NULL,
                VAT REAL NOT NULL,
                PriceBrutto REAL NOT NULL,
                Stock INTEGER NOT NULL,
                FOREIGN KEY (DepartmentID) REFERENCES department(ID)
            )
        """

        private const val CREATE_TAXES_TABLE = """
            CREATE TABLE taxes (
                ID INTEGER PRIMARY KEY,
                Name TEXT NOT NULL,
                Value REAL NOT NULL
            )
        """

        private const val CREATE_USERS_TABLE = """
            CREATE TABLE users (
                ID INTEGER PRIMARY KEY,
                Type TEXT,
                Name TEXT NOT NULL,
                Password TEXT NOT NULL
            )
        """

        private const val CREATE_USER_TYPE_TABLE = """
            CREATE TABLE user_type (
                ID INTEGER PRIMARY KEY,
                Name TEXT NOT NULL
            )
        """

        private const val CREATE_SELLING_PROCESS_TABLE = """
            CREATE TABLE selling_process (
                ID INTEGER PRIMARY KEY,
                Type INTEGER,
                ProductNumber TEXT,
                Quantity INTEGER NOT NULL,
                PriceSell REAL NOT NULL,
                Amount REAL NOT NULL,
                UserID INTEGER,
                FOREIGN KEY (Type) REFERENCES selling_process_type(ID),
                FOREIGN KEY (UserID) REFERENCES users(ID),
                FOREIGN KEY (ProductNumber) REFERENCES products(ProductNumber)
            )
        """

        private const val CREATE_SELLING_PROCESS_TYPE_TABLE = """
            CREATE TABLE selling_process_type (
                ID INTEGER PRIMARY KEY,
                Name TEXT NOT NULL
            )
        """

        private const val CREATE_TENDER_TABLE = """
            CREATE TABLE Tender (
                ID INTEGER PRIMARY KEY,
                Content TEXT NOT NULL CHECK(Content IN ('Cash', 'Card', 'Other'))
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_GROUP_TABLE)
        db?.execSQL(CREATE_DEPARTMENT_TABLE)
        db?.execSQL(CREATE_PRODUCTS_TABLE)
        db?.execSQL(CREATE_TAXES_TABLE)
        db?.execSQL(CREATE_USERS_TABLE)
        db?.execSQL(CREATE_USER_TYPE_TABLE)
        db?.execSQL(CREATE_SELLING_PROCESS_TABLE)
        db?.execSQL(CREATE_SELLING_PROCESS_TYPE_TABLE)
        db?.execSQL(CREATE_TENDER_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS groupp")
        db?.execSQL("DROP TABLE IF EXISTS department")
        db?.execSQL("DROP TABLE IF EXISTS products")
        db?.execSQL("DROP TABLE IF EXISTS taxes")
        db?.execSQL("DROP TABLE IF EXISTS users")
        db?.execSQL("DROP TABLE IF EXISTS user_type")
        db?.execSQL("DROP TABLE IF EXISTS selling_process")
        db?.execSQL("DROP TABLE IF EXISTS selling_process_type")
        db?.execSQL("DROP TABLE IF EXISTS Tender")
        onCreate(db)
    }

    fun insertUser(username: String , password: String): Long {
        val values = ContentValues().apply {
            put("Type", "user")
            put("Name", username)
            put("Password", password)
        }
        val db = writableDatabase
        return db.insert("users", null,values)
    }
    fun readUser(username: String, password: String): Boolean{
        val db = readableDatabase
        val selection = "Name = ? AND Password = ?"
        val selectionArgs = arrayOf(username ,password)
        val cursor = db.query("users", null, selection, selectionArgs, null, null, null )

        val usersExist = cursor.count > 0
        cursor.close()
        return usersExist
    }

    fun insertGroup(groupName: String): Long {
        val values = ContentValues().apply {
            put("Name", groupName)
        }
        val db = writableDatabase
        return db.insert("groupp", null,values)
    }
    fun insertDepartment(departmentName: String): Long {
        val values = ContentValues().apply {
            put("Name", departmentName)
        }
        val db = writableDatabase
        return db.insert("department", null,values)
    }
}