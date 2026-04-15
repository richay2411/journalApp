 package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

 @Document(collection = "config_journal_app")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@EqualsAndHashCode
//@Builder
@Data
@NoArgsConstructor
public class ConfigJournalAppEntity {
   private String key;
   private String value;


}
