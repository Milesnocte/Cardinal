using System.Data;
using Npgsql;

public class AppDBContext
{
    private readonly string _connectionString = Environment.GetEnvironmentVariable("DB_CONNECTION")!;
    private NpgsqlConnection _connection;

    public AppDBContext()
    {
        _connection = new NpgsqlConnection(_connectionString);
    }
    
    public IDbConnection Connection()
        => _connection;
}