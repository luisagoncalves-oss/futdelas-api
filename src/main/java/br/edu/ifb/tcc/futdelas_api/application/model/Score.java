package br.edu.ifb.tcc.futdelas_api.application.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Score {
   private Integer current;
   private Integer display;
   private Integer period1;
   private Integer period2;
   private Integer normaltime; 
}