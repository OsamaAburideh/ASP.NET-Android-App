using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using EAD2_Project_API;
using EAD2_Project_API.Data;

namespace EAD2_Project_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TeamsController : ControllerBase
    {
        private readonly EAD2_Project_APIContext _context;

        public TeamsController(EAD2_Project_APIContext context)
        {
            _context = context;
        }

        // GET: api/Teams
        [HttpGet]
        public IEnumerable<Team> GetTeam()
        {
            return  _context.Team.Include(c => c.Players);
        }

        // GET: api/Teams/5
        [HttpGet("{teamname}")]
        public ActionResult<string> GetTeam(string teamname)
        {
            Team team = _context.Team.FirstOrDefault(t => t.teamname.ToLower() == teamname);

            if (team == null)
            {
                return NotFound();
            }

            return Ok(team);
        }

        

        // POST: api/Teams
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Team>> PostTeam(Team team)
        {
            _context.Team.Add(team);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetTeam", new { id = team.teamId }, team);
        }

        // DELETE: api/Teams/5
        [HttpDelete("{teamname}")]
        // do same as playername
        public async Task<IActionResult> DeleteTeam(string teamname)
        {
            Team team = _context.Team.FirstOrDefault(t => t.teamname.ToLower() == teamname);
            if (team == null)
            {
                return NotFound();
            }

            _context.Team.Remove(team);
            await _context.SaveChangesAsync();

            return NoContent();
        }
        /*// PUT: api/Teams/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutTeam(int id, Team team)
        {
            if (id != team.teamId)
            {
                return BadRequest();
            }

            _context.Entry(team).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!TeamExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }
        private bool TeamExists(int id)
        {
            return _context.Team.Any(e => e.teamId == id);
        }*/
    }
}
