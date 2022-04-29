using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
namespace EAD2_Project_API
{
    public class Player
    {
        [Required]
        [Key]
        public int playerId { get; set; }

        public string playername { get; set; }
        public string position { get; set; }
        public int age { get; set; }
    }

    public class Team 
    {
        [Required]
        [Key]
        public int teamId { get; set; }
        public string teamname { get; set; }

        public string league { get; set; }

        public int total_games { get; set; }

        public int wins { get; set; }

        public int loss { get; set; }

        [ForeignKey("playerId")]
        public Player Players { get; set; }
    }
}

