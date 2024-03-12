namespace DiscordBot.Data.Models;

public class Dictionary
{
    public class Cx
    {
        public string cxl { get; set; }
        public List<Cxti> cxtis { get; set; }
    }

    public class Cxti
    {
        public string cxt { get; set; }
    }

    public class Def
    {
        public List<List<List<object>>> sseq { get; set; }
    }

    public class Dro
    {
        public string drp { get; set; }
        public List<Def> def { get; set; }
        public List<Pr> prs { get; set; }
        public List<Vr> vrs { get; set; }
    }

    public class Hwi
    {
        public string hw { get; set; }
        public List<Pr> prs { get; set; }
    }

    public class In
    {
        public string @if { get; set; }
        public List<Pr> prs { get; set; }
    }

    public class Meta
    {
        public string id { get; set; }
        public string uuid { get; set; }
        public string sort { get; set; }
        public string src { get; set; }
        public string section { get; set; }
        public List<string> stems { get; set; }
        public bool offensive { get; set; }
    }

    public class Pr
    {
        public string mw { get; set; }
        public Sound sound { get; set; }
    }

    public class Definition
    {
        public Meta meta { get; set; }
        public int hom { get; set; }
        public Hwi hwi { get; set; }
        public string fl { get; set; }
        public List<In> ins { get; set; }
        public List<Def> def { get; set; }
        public List<Uro> uros { get; set; }
        public List<Dro> dros { get; set; }
        public List<Usage> usages { get; set; }
        public List<List<object>> et { get; set; }
        public string date { get; set; }
        public List<string> shortdef { get; set; }
        public List<Cx> cxs { get; set; }
        public List<string> lbs { get; set; }
    }

    public class Sound
    {
        public string audio { get; set; }
        public string @ref { get; set; }
        public string stat { get; set; }
    }

    public class Uro
    {
        public string ure { get; set; }
        public List<Pr> prs { get; set; }
        public string fl { get; set; }
    }

    public class Usage
    {
        public string pl { get; set; }
        public List<List<object>> pt { get; set; }
    }

    public class Vr
    {
        public string vl { get; set; }
        public string va { get; set; }
    }
}