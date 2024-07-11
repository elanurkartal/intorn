package com.example.intorn

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.intorn.masterData.Department_model
import com.example.intorn.masterData.Group_model
import com.example.intorn.masterData.article.ArticleAdapter
import com.example.intorn.masterData.article.ArticleModel
import com.example.intorn.staff.UserModel

class DatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

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
                VAT TEXT NOT NULL,
                PriceBrutto DOUBLE NOT NULL,
                Stock INTEGER NOT NULL,
                FOREIGN KEY (DepartmentID) REFERENCES department(ID)
            )
        """

        private const val CREATE_TAXES_TABLE = """
            CREATE TABLE taxes (
                ID INTEGER PRIMARY KEY,
                Name TEXT NOT NULL,
                Value DOUBLE NOT NULL
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

    fun insertUser(
        username: String,
        password: String
    ): Long { //long insert sonucu dönen verinin tipini belirtiyor.
        val values = ContentValues().apply {
            put("Type", "user")
            put("Name", username)
            put("Password", password)
        }
        val db = writableDatabase
        return db.insert("users", null, values)
    }

    fun readUser(username: String, password: String): Boolean {
        val db = readableDatabase
        val selection = "Name = ? AND Password = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query("users", null, selection, selectionArgs, null, null, null)

        val usersExist = cursor.count > 0
        cursor.close()
        return usersExist
    }

    fun getUsers(): List<UserModel> {
        val db = readableDatabase
        val userList = ArrayList<UserModel>()
        val cursor = db.rawQuery("SELECT * FROM users", null)
        val typeIndex = cursor.getColumnIndex("Type")
        val nameIndex = cursor.getColumnIndex("Name")

        if (typeIndex == -1 || nameIndex == -1) {
            cursor.close()
            throw IllegalArgumentException("Veritabanında 'type' veya 'name' sütunları bulunamadı.")
        }

        if (cursor.moveToFirst()) {
            do {
                val user = UserModel(
                    cursor.getString(nameIndex),
                    cursor.getString(typeIndex)
                )
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

    fun insertGroup(groupName: String): Long {
        val values = ContentValues().apply {
            put("Name", groupName)
        }
        val db = writableDatabase
        return db.insert("groupp", null, values)
    }

    fun insertDepartment(departmentName: String): Long {
        val values = ContentValues().apply {
            put("Name", departmentName)
        }
        val db = writableDatabase
        return db.insert("department", null, values)
    }


    fun getGroups(): List<String> {
        val db = readableDatabase
        val sql = "select name from groupp"
        val list = mutableListOf<String>()
        db.rawQuery(sql, null).use {
            while (it.moveToNext()) {
                list.add(it.getString(0))

            }
        }
        return list
    }

    fun getDepartments(): List<String> {
        val db = readableDatabase
        val sql = "select name from department"
        val list = mutableListOf<String>()
        db.rawQuery(sql, null).use {
            while (it.moveToNext()) {
                list.add(it.getString(0))

            }
        }
        return list
    }
    fun getDepartmentName(): List<Department_model> {
        val db = readableDatabase
        val departmentList = ArrayList<Department_model>()
        val cursor = db.rawQuery("SELECT * FROM department", null)
        val nameIndex = cursor.getColumnIndex("Name")

        if (nameIndex == -1) {
            cursor.close()
            throw IllegalArgumentException("Veritabanında 'name' sütunu bulunamadı.")
        }

        if (cursor.moveToFirst()) {
            do {
                val department = Department_model(
                    cursor.getString(nameIndex),

                    )
                departmentList.add(department)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return departmentList
    }

    fun getGroupsName(): List<Group_model> {
        val db = readableDatabase
        val groupList = ArrayList<Group_model>()
        val cursor = db.rawQuery("SELECT * FROM groupp", null)
        val nameIndex = cursor.getColumnIndex("Name")

        if (nameIndex == -1) {
            cursor.close()
            throw IllegalArgumentException("Veritabanında 'name' sütunu bulunamadı.")
        }

        if (cursor.moveToFirst()) {
            do {
                val group = Group_model(
                    cursor.getString(nameIndex),

                )
                groupList.add(group)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return groupList
    }

    fun getTaxes(): List<String> {
        val db = readableDatabase
        val sql = "select name from taxes"
        val list = mutableListOf<String>()
        db.rawQuery(sql, null).use {
            while (it.moveToNext()) {
                list.add(it.getString(0))

            }
        }
        return list
    }
    fun insertTaxes(taxesName: String, taxesRate: Double): Long {
        val values = ContentValues().apply {
            put("Name", taxesName)
            put("Value", taxesRate)
        }
        val db = writableDatabase
        return db.insert("taxes", null, values)
    }
    fun insertArticle(departmentId: Int, productNumber: String, name: String, vat: String, price:Double, stock:Int ): Long {
        val values = ContentValues().apply {
            put("DepartmentID", departmentId)
            put("ProductNumber", productNumber)
            put("Name", name)
            put("VAT", vat)
            put("PriceBrutto", price)
            put("Stock", stock)

        }
        val db = writableDatabase
        return db.insert("products", null, values)
    }
    fun getDepartmentId(departmentName:String):Int{
        val db = readableDatabase
        var departmentId = -1
        val query = "select * from department WHERE Name = '$departmentName'"
        val cursor: Cursor = db.rawQuery(query, null)
        val nameIndex = cursor.getColumnIndex("ID")

        if (nameIndex== -1) {
            cursor.close()
            throw IllegalArgumentException("Veritabanında 'name' sütunu bulunamadı.")
        }
        if (cursor.moveToFirst()) {
            departmentId = cursor.getInt(nameIndex)
        }

        cursor.close()
        db.close()

        return departmentId
    }

    fun getArticles(): List<ArticleModel> {
        val db = readableDatabase
        val articleList = ArrayList<ArticleModel>()
        val cursor = db.rawQuery("SELECT * FROM products", null)
        val nameIndex = cursor.getColumnIndex("Name")
        val pluIndex = cursor.getColumnIndex("ProductNumber")
        val stockIndex = cursor.getColumnIndex("Stock")

        if (nameIndex == -1 || pluIndex == -1 || stockIndex == -1) {
            cursor.close()
            throw IllegalArgumentException("Veritabanında 'name', 'plu' ve 'stock' sütunları bulunamadı.")
        }

        if (cursor.moveToFirst()) {
            do {
                val article = ArticleModel(
                    cursor.getString(nameIndex),
                    cursor.getString(pluIndex),
                    cursor.getString(stockIndex)
                    )
                articleList.add(article)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return articleList
    }

}