using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using EAD2_Project_API;

namespace EAD2_Project_API.Data
{
    public class EAD2_Project_APIContext : DbContext
    {
        public EAD2_Project_APIContext (DbContextOptions<EAD2_Project_APIContext> options)
            : base(options)
        {
        }

        public DbSet<EAD2_Project_API.Player> Player { get; set; }

        public DbSet<EAD2_Project_API.Team> Team { get; set; }
    }
}
