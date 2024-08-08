package com.example.intorn

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.intorn.masterData.Department_model
import com.example.intorn.masterData.Group_model
import com.example.intorn.masterData.Taxes_Model
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
                Type TEXT,
                ProductNumber TEXT,
                Quantity INTEGER NOT NULL,
                PriceSell DOUBLE NOT NULL,
                Amount DOUBLE NOT NULL,
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
        db?.execSQL("INSERT INTO users (Type,Name,Password) VALUES('Manager','admin','123')")


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

    fun insertUser(username: String, password: String, type: String): Long { //long insert sonucu dönen verinin tipini belirtiyor.
        val values = ContentValues().apply {
            put("Type", type)
            put("Name", username)
            put("Password", password)
        }
        val db = writableDatabase
        return db.insert("users", null, values)
    }
    fun updateUser(newName: String, password: String, type: String, oldName: String){
        val db = writableDatabase
        val tableName = "users"
        val whereClause = "Name = ?"
        val whereArgs = arrayOf(oldName)
        val values = ContentValues().apply {
            put("Type", type)
            put("Name", newName)
            put("Password", password)
        }
        db.update(tableName,values,whereClause,whereArgs)
        db.close()
    }
    fun updateGroup(newName: String, oldName: String){
        val db = writableDatabase
        val tableName = "groupp"
        val whereClause = "Name = ?"
        val whereArgs = arrayOf(oldName)
        val values = ContentValues().apply {
            put("Name", newName)
        }
        db.update(tableName,values,whereClause,whereArgs)
        db.close()
    }
    fun updateDepartment(newName: String, oldName: String,groupId: Int){
        val db = writableDatabase
        val tableName = "department"
        val whereClause = "Name = ?"
        val whereArgs = arrayOf(oldName)
        val values = ContentValues().apply {
            put("Name", newName)
            put("GroupId", groupId)
        }
        db.update(tableName,values,whereClause,whereArgs)
        db.close()
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
    fun findUser(username: String): Boolean {
        val db = readableDatabase
        val selection = "Name = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query("users", null, selection, selectionArgs, null, null, null)

        val exist = cursor.count > 0
        cursor.close()
        return exist
    }
    fun findDepartment(departmentName: String): Boolean {
        val db = readableDatabase
        val selection = "Name = ?"
        val selectionArgs = arrayOf(departmentName)
        val cursor = db.query("department", null, selection, selectionArgs, null, null, null)

        val exist = cursor.count > 0
        cursor.close()
        return exist
    }
    fun findGroup(groupName: String): Boolean {
        val db = readableDatabase
        val selection = "Name = ?"
        val selectionArgs = arrayOf(groupName)
        val cursor = db.query("groupp", null, selection, selectionArgs, null, null, null)

        val exist = cursor.count > 0
        cursor.close()
        return exist
    }
    fun findTaxes(taxesName: String): Boolean {
        val db = readableDatabase
        val selection = "Name = ?"
        val selectionArgs = arrayOf(taxesName)
        val cursor = db.query("taxes", null, selection, selectionArgs, null, null, null)

        val exist = cursor.count > 0
        cursor.close()
        return exist
    }
    fun findProduct(productName: String, productNumber: String): Boolean {
        val db = readableDatabase
        val selection = "Name = ? OR ProductNumber = ?"
        val selectionArgs = arrayOf(productName,productNumber)
        val cursor = db.query("products", null, selection, selectionArgs, null, null, null)

        val exist = cursor.count > 0
        cursor.close()
        return exist
    }
    fun readSellingProcess(productId: String, userID: String, type: String): Boolean {
        val db = readableDatabase
        val selection = "ProductNumber = ? AND UserID = ? AND Type = ?"
        val selectionArgs = arrayOf(productId, userID, type)
        val cursor = db.query("selling_process", null, selection, selectionArgs, null, null, null)

        val productExist = cursor.count > 0
        cursor.close()
        return productExist
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
    fun getProducts(): List<HomeModel> {
        val db = readableDatabase
        val productList = ArrayList<HomeModel>()
        val cursor = db.rawQuery("SELECT * FROM products", null)
        val priceIndex = cursor.getColumnIndex("PriceBrutto")
        val nameIndex = cursor.getColumnIndex("Name")
        val stockIndex = cursor.getColumnIndex("Stock")

        if (priceIndex == -1 || nameIndex == -1 || stockIndex == -1) {
            cursor.close()
            throw IllegalArgumentException("Veritabanında 'price', 'name' veya 'stock' sütunları bulunamadı.")
        }

        if (cursor.moveToFirst()) {
            do {
                val product = HomeModel(
                    cursor.getString(nameIndex),
                    cursor.getString(priceIndex),
                    cursor.getInt(stockIndex)
                )
                productList.add(product)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return productList
    }

    fun insertGroup(groupName: String): Long {
        val values = ContentValues().apply {
            put("Name", groupName)
        }
        val db = writableDatabase
        return db.insert("groupp", null, values)
    }

    fun insertDepartment(departmentName: String, groupId: Int): Long {
        val values = ContentValues().apply {
            put("Name", departmentName)
            put("GroupID", groupId)
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
    fun deleteUser(userName: String): Boolean {
        val db = writableDatabase
        val tableName = "users"
        val whereClause = "Name = ?"
        val whereArgs = arrayOf(userName)

        return try {
            val rowsDeleted = db.delete(tableName, whereClause, whereArgs)
            rowsDeleted > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            db.close()
        }
    }

    fun deleteGroup(groupName: String): Boolean {
        val db = writableDatabase
        val tableName = "groupp"
        val whereClause = "Name = ?"
        val whereArgs = arrayOf(groupName)

        return try {
            val rowsDeleted = db.delete(tableName, whereClause, whereArgs)
            rowsDeleted > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            db.close()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun deleteDepartment(departmentName: String): Boolean {
        val db = writableDatabase
        val tableName = "department"
        val whereClause = "Name = ?"
        val whereArgs = arrayOf(departmentName)

        return try {
            val rowsDeleted = db.delete(tableName, whereClause, whereArgs)
                rowsDeleted > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            db.close()
        }
    }
    fun deleteTaxes(taxesName: String): Boolean {
        val db = writableDatabase
        val tableName = "taxes"
        val whereClause = "Name = ?"
        val whereArgs = arrayOf(taxesName)

        return try {
            val rowsDeleted = db.delete(tableName, whereClause, whereArgs)
            rowsDeleted > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            db.close()
        }
    }
    fun deleteArticle(articleName: String): Boolean {
        val db = writableDatabase
        val tableName = "products"
        val whereClause = "Name = ?"
        val whereArgs = arrayOf(articleName)

        return try {
            val rowsDeleted = db.delete(tableName, whereClause, whereArgs)
            rowsDeleted > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            db.close()
        }
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

    fun getTaxesName(): List<Taxes_Model> {
        val db = readableDatabase
        val taxesList = ArrayList<Taxes_Model>()
        val cursor = db.rawQuery("SELECT * FROM taxes", null)
        val nameIndex = cursor.getColumnIndex("Name")

        if (nameIndex == -1) {
            cursor.close()
            throw IllegalArgumentException("Veritabanında 'name' sütunu bulunamadı.")
        }

        if (cursor.moveToFirst()) {
            do {
                val taxes = Taxes_Model(
                    cursor.getString(nameIndex),

                    )
                taxesList.add(taxes)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return taxesList
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
    fun getUserRole(userName:String):String{
        val db = readableDatabase
        var userRole = ""
        val query = "select * from users WHERE Name = '$userName'"
        val cursor: Cursor = db.rawQuery(query, null)
        val nameIndex = cursor.getColumnIndex("Type")

        if (nameIndex== -1) {
            cursor.close()
            throw IllegalArgumentException("Veritabanında 'type' sütunu bulunamadı.")
        }
        if (cursor.moveToFirst()) {
            userRole = cursor.getString(nameIndex)
        }

        cursor.close()
        db.close()

        return userRole
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
    fun getGroupIdFromGroupName(groupName: String):Int{
        val db = readableDatabase
        var groupId = -1
        val query = "select * from groupp WHERE Name = '$groupName'"
        val cursor: Cursor = db.rawQuery(query, null)
        val nameIndex = cursor.getColumnIndex("ID")

        if (nameIndex== -1) {
            cursor.close()
            throw IllegalArgumentException("Veritabanında 'name' sütunu bulunamadı.")
        }
        if (cursor.moveToFirst()) {
            groupId = cursor.getInt(nameIndex)
        }

        cursor.close()
        db.close()

        return groupId
    }
    fun getGroupNameFromDepartmentName(departmentName: String):String{
        val db = readableDatabase
        var groupId = -1
        val query = "select * from department WHERE Name = '$departmentName'"
        val cursor: Cursor = db.rawQuery(query, null)
        val nameIndex = cursor.getColumnIndex("GroupID")

        if (nameIndex== -1) {
            cursor.close()
            throw IllegalArgumentException("Veritabanında 'name' sütunu bulunamadı.")
        }
        if (cursor.moveToFirst()) {
            groupId = cursor.getInt(nameIndex)
        }

        cursor.close()
        db.close()
        val groupName = getGroupNameFromGroupId(groupId)

        return groupName
    }
    private fun getGroupNameFromGroupId(groupId: Int):String{
        val db = readableDatabase
        var groupName = ""
        val query = "select * from groupp WHERE ID = '$groupId'"
        val cursor: Cursor = db.rawQuery(query, null)
        val nameIndex = cursor.getColumnIndex("Name")

        if (nameIndex== -1) {
            cursor.close()
            throw IllegalArgumentException("Veritabanında 'name' sütunu bulunamadı.")
        }
        if (cursor.moveToFirst()) {
            groupName = cursor.getString(nameIndex)
        }

        cursor.close()
        db.close()

        return groupName
    }
    fun getUserIdFromUserName(userName: String):Int{
        val db = readableDatabase
        var userID = 0
        val query = "select * from users WHERE Name = '$userName'"
        val cursor: Cursor = db.rawQuery(query, null)
        val nameIndex = cursor.getColumnIndex("ID")

        if (nameIndex== -1) {
            cursor.close()
            throw IllegalArgumentException("Veritabanında 'name' sütunu bulunamadı.")
        }
        if (cursor.moveToFirst()) {
            userID = cursor.getInt(nameIndex)
        }

        cursor.close()
        db.close()

        return userID
    }
        fun getProductId(productName:String):Int{
        val db = readableDatabase
        var productId = -1
        val query = "select * from products WHERE Name = '$productName'"
        val cursor: Cursor = db.rawQuery(query, null)
        val nameIndex = cursor.getColumnIndex("ProductNumber")

        if (nameIndex== -1) {
            cursor.close()
            throw IllegalArgumentException("Veritabanında 'name' sütunu bulunamadı.")
        }
        if (cursor.moveToFirst()) {
            productId = cursor.getInt(nameIndex)
        }

        cursor.close()
        db.close()

        return productId
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
    fun getSellingProcess(): List<SellingProcessModel> {
        val db = readableDatabase
        val sellingList = ArrayList<SellingProcessModel>()
        val cursor = db.rawQuery("SELECT * FROM selling_process", null)
        val productId = cursor.getColumnIndex("ProductNumber")
        val quantityIndex = cursor.getColumnIndex("Quantity")
        val priceIndex = cursor.getColumnIndex("PriceSell")

        if (productId == -1 || quantityIndex == -1 || priceIndex == -1) {
            cursor.close()
            Log.v("hata","Veritabanında 'name', 'plu' ve 'stock' sütunları bulunamadı.")
        }

        if (cursor.moveToFirst()) {
            do {
                val selling = SellingProcessModel(
                    getProductName(cursor.getString(productId)),
                    cursor.getString(priceIndex).toDouble(),
                    cursor.getString(quantityIndex).toInt(),
                    productId
                )
                sellingList.add(selling)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return sellingList
    }

    fun insertSellingProcess(type: String, price: String, productId: String, quantity: String, userID:Int): Long {
        val values = ContentValues().apply {
            put("Type", type)
            put("ProductNumber", productId)
            put("Quantity", quantity)
            put("PriceSell", price.toDouble())
            put("Amount", quantity.toInt()*price.toDouble())
            put("UserID", userID)
        }
        val db = writableDatabase
        return db.insert("selling_process", null, values)
    }

    fun updateSellingProcess(productId: String, price: String, userID: String, type: String, quantity: Int){
        val db = writableDatabase
        val tableName = "selling_process"
        val whereClause = "ProductNumber = ? AND UserID = ? AND Type = ?"
        val whereArgs = arrayOf(productId, userID, type)
        val values = ContentValues().apply {
            put("Quantity", (getQuantity(productId, userID, type)+quantity))
            put("Amount", (getQuantity(productId, userID, type)+quantity)*price.toDouble())

        }
        db.update(tableName,values,whereClause,whereArgs)
        db.close()
    }
    fun getQuantity(productId:String, userID: String, type: String):Int{
        val db = readableDatabase
        var quantity = -1
        val query = "select * from selling_process WHERE ProductNumber = '$productId' and UserID = '$userID' and Type = '$type'"
        val cursor: Cursor = db.rawQuery(query, null)
        val amountIndex = cursor.getColumnIndex("Quantity")
        if (cursor.moveToFirst()) {
            quantity = cursor.getInt(amountIndex)
        }

        return quantity
    }
    fun getProductName(productId:String):String{
        val db2 = readableDatabase
        var name = ""
        val query = "select * from products WHERE ProductNumber = '$productId'"
        val cursor2: Cursor = db2.rawQuery(query, null)
        val nameIndex = cursor2.getColumnIndex("Name")
        if (cursor2.moveToFirst()) {
            name = cursor2.getString(nameIndex)
        }

        return name
    }

    fun getProductStock(productId: String): Int{
        val db2 = readableDatabase
        var productStock = -1
        val query = "select * from products WHERE ProductNumber = '$productId'"
        val cursor2: Cursor = db2.rawQuery(query, null)
        val stockIndex = cursor2.getColumnIndex("Stock")
        if (cursor2.moveToFirst()) {
            productStock = cursor2.getInt(stockIndex)
        }

        return productStock
    }
    fun getProductPrice(productId: String): Int{
        val db2 = readableDatabase
        var productPrice = -1
        val query = "select * from products WHERE ProductNumber = '$productId'"
        val cursor2: Cursor = db2.rawQuery(query, null)
        val stockIndex = cursor2.getColumnIndex("PriceBrutto")
        if (cursor2.moveToFirst()) {
            productPrice = cursor2.getInt(stockIndex)
        }

        return productPrice
    }

    fun updateProductStock(productId: String, stock: Int) {
        val db = writableDatabase
        val tableName = "products"
        val whereClause = "ProductNumber = ?"
        val whereArgs = arrayOf(productId)
        val values = ContentValues().apply {
            put("Stock", stock)
        }
        db.update(tableName,values,whereClause,whereArgs)
        db.close()
    }

    fun addTaxesToTotal(kdv: Double, otv: Double, productId: String) {
        val db = writableDatabase
        val tableName = "products"
        val whereClause = "ProductNumber = ?"
        val whereArgs = arrayOf(productId)

        // Ürünün mevcut KDV ve ÖTV değerlerini al
        val currentKDV = getKDV(productId)
        val currentOTV = getOTV(productId)

        // Yeni KDV ve ÖTV değerlerini hesapla ve güncelle
        val newKDV = currentKDV + kdv
        val newOTV = currentOTV + otv

        val values = ContentValues().apply {
            put("VAT", newKDV.toString())
            put("PriceBrutto", calculatePriceBrutto(productId, newKDV, newOTV)) // Yeni brutto fiyatını hesapla
        }

        db.update(tableName, values, whereClause, whereArgs)
        db.close()
    }

    // Ürünün mevcut KDV değerini getir
    private fun getKDV(productId: String): Double {
        val db = readableDatabase
        var kdv = 0.0
        val query = "select VAT from products WHERE ProductNumber = '$productId'"
        val cursor: Cursor = db.rawQuery(query, null)
        val vatIndex = cursor.getColumnIndex("VAT")

        if (cursor.moveToFirst()) {
            kdv = cursor.getDouble(vatIndex)
        }

        cursor.close()
        db.close()

        return kdv
    }

    // Ürünün mevcut ÖTV değerini getir
    private fun getOTV(productId: String): Double {
        val db = readableDatabase
        var otv = 0.0
        val query = "select OTV from products WHERE ProductNumber = '$productId'"
        val cursor: Cursor = db.rawQuery(query, null)
        val otvIndex = cursor.getColumnIndex("OTV")

        if (cursor.moveToFirst()) {
            otv = cursor.getDouble(otvIndex)
        }

        cursor.close()
        db.close()

        return otv
    }

    fun getTaxesRate(productId: String): Double {
        val db = readableDatabase
        var rate = 0.0
        val query = "select * from products WHERE ProductNumber = '$productId'"
        val cursor: Cursor = db.rawQuery(query, null)
        val vatIndex = cursor.getColumnIndex("VAT")

        if (cursor.moveToFirst()) {
            rate = getTaxesRateFromName(cursor.getString(vatIndex))
        }

        cursor.close()
        db.close()

        return rate
    }
    private fun getTaxesRateFromName(name: String): Double {
        val db = readableDatabase
        var rate = 0.0
        var vatName = ""
        val query = "select * from taxes WHERE Name = '$name'"
        val cursor: Cursor = db.rawQuery(query, null)
        val rateIndex = cursor.getColumnIndex("Value")

        if (cursor.moveToFirst()) {
            rate = cursor.getDouble(rateIndex)
        }


        return rate
    }

    // Yeni brutto fiyatını hesapla (PriceBrutto)
    private fun calculatePriceBrutto(productId: String, newKDV: Double, newOTV: Double): Double {
        val db = readableDatabase
        var netPrice = 0.0
        val query = "select PriceBrutto from products WHERE ProductNumber = '$productId'"
        val cursor: Cursor = db.rawQuery(query, null)
        val priceIndex = cursor.getColumnIndex("PriceBrutto")

        if (cursor.moveToFirst()) {
            netPrice = cursor.getDouble(priceIndex)
        }

        cursor.close()
        db.close()

        // Yeni brutto fiyatı hesaplama formülü (örnek olarak)
        val newPriceBrutto = netPrice * (1 + newKDV/100 + newOTV/100)

        return newPriceBrutto
    }


}