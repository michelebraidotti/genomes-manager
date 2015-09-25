package org.genomesmanager.patches
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import javax.sql.DataSource

ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring/genomes-manager-integration-appContext.xml")
DataSource dataSource = (DataSource) ctx.getBean("dataSource")

// Recipe
// 1. Alter table sequences add name
// 2. move all names from scaffolds to sequences
// 3. move all names from pseudomols to sequences
def sql = "ALTER TABLE sequences ADD COLUMN name varchar(55)"
sql = "select * from species"
def stmt = dataSource.connection.createStatement()
rs = stmt.executeQuery(sql)
while(rs.next()){
         println rs.getString("common_name")
}