﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace SoftUniBlog.Models
{
    public class Article
    {
        public Article()
        {
            this.DateAdded = DateTime.Now;
        }

        [Key]
        public int Id { get; set; }

        [Required]
        [StringLength(50)]
        public string Title { get; set; }

        [Required]
        public string Content { get; set; }

        public bool IsAuthor(string name)
        {
            return this.Author.UserName.Equals(name);
        }

        public DateTime DateAdded { get; set; }

        [ForeignKey("Author")]
        public string AuthorId { get; set; }

        public ApplicationUser Author { get; set; }
    }
}