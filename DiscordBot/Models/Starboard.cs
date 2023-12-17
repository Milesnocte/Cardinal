using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Models;

[Table("starboard")]
public class Starboard
{
    [Column("guild_id")]
    public string GuildId { get; set; }
    
    [Key]
    [Column("message_id")]
    public string MessageId { get; set; }
    
    [Column("author")]
    public string Author { get; set; }
    
    [Column("stars")]
    public int stars { get; set; }
    
    [Column("posted")]
    public int Posted { get; set; }
    
    [Column("date")]
    public DateTime date { get; set; }
}