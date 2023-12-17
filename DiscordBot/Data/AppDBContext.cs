using System.Data;
using Npgsql;

public class AppDBContext
{
    private readonly string _connectionString = Environment.GetEnvironmentVariable("DB_CONNECTION")!;
    
    public IDbConnection Connection()
        => new NpgsqlConnection(_connectionString);
}