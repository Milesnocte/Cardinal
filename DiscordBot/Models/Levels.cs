using System.ComponentModel.DataAnnotations.Schema;

namespace Models;

[Table("levels")]
public class Levels
{
    [Column("member")]
    public string Member { get; set; }
    
    [Column("server")]
    public string Server { get; set; }
    
    [Column("xp")]
    public int Xp { get; set; }
}