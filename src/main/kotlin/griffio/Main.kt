package griffio

import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import griffio.migrations.Scores
import griffio.queries.Sample
import org.postgresql.ds.PGSimpleDataSource

private fun getSqlDriver() = PGSimpleDataSource().apply {
    setURL("jdbc:postgresql://localhost:5432/windowfuncs")
    applicationName = "App Main"
}.asJdbcDriver()

fun main() {
    val driver = getSqlDriver()
    val sample = Sample(driver)
    sample.scoresQueries.insert(Scores("Player 1", 99))
    sample.scoresQueries.insert(Scores("Player 2", 12))
    sample.scoresQueries.insert(Scores("Player 3", 93))
    sample.scoresQueries.insert(Scores("Player 4", 17))
    sample.scoresQueries.insert(Scores("Player 5", 103))
    sample.scoresQueries.insert(Scores("Player 6", 4))
    println(sample.scoresQueries.select().executeAsList().joinToString("\n"))
    println(sample.scoresQueries.selectAvgByPartition().executeAsList().joinToString("\n"))
    println(sample.scoresQueries.selectSumByPartition().executeAsList().joinToString("\n"))
}
