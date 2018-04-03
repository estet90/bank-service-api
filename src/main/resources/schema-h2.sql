CREATE TABLE contact (
  contact_id BIGINT AUTO_INCREMENT PRIMARY KEY);

CREATE TABLE application (
  application_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
  contact_id BIGINT NOT NULL,
  product_name VARCHAR(100) NOT NULL,
  dt_created TIMESTAMP NOT NULL,
  FOREIGN KEY (contact_id) REFERENCES contact(contact_id));
