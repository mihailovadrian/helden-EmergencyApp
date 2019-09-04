CREATE TABLE client_mlv (
	ID_client number(5),
	Name varchar2(100),
	Longitude varchar2(50),
	Latitude varchar2(50),
	Status number(1),
	Location_Id number(5),
	constraint CLIENT_PK PRIMARY KEY (ID_client));
CREATE sequence "CLIENT_SEQ"
/
CREATE trigger BI_CLIENT
  before insert on client_mlv
  for each row
begin
  select "CLIENT_SEQ".nextval into :NEW.id_client from dual;
end;
/

)
/
CREATE TABLE Location_mlv (
	Location_id number(5),
	Name varchar2(100),
	constraint LOCATION_PK_MLV PRIMARY KEY (Location_id));


CREATE sequence "LOCATION_SEQ"
/
CREATE trigger "BI_LOCATION"
  before insert on location_mlv
  for each row
begin
  select "LOCATION_SEQ".nextval into :NEW.Location_id from dual;
end;
/

)
/
CREATE TABLE Events_mlv (
	Event_id number(5),
	Name varchar2(100),
	Description varchar2(100),
	Status number(1),
	constraint EVENTS_PK PRIMARY KEY (Event_id));
CREATE sequence "EVENTS_SEQ"
/
CREATE trigger "BI_EVENTS"
  before insert on events_mlv
  for each row
begin
  select "EVENTS_SEQ".nextval into :NEW.Event_id from dual;
end;
/

)
/
CREATE TABLE EventLocation_mlv (
	Event_id number(5),
	Location_id number(5));
/
ALTER TABLE client_mlv ADD CONSTRAINT "Client_fk0" FOREIGN KEY (Location_Id) REFERENCES Location_mlv(Location_id);





ALTER TABLE EventLocation_mlv ADD CONSTRAINT "EventLocation_fk0" FOREIGN KEY (Event_id) REFERENCES Events_mlv(Event_id);
ALTER TABLE EventLocation_mlv ADD CONSTRAINT "EventLocation_fk1" FOREIGN KEY (Location_id) REFERENCES Location_mlv(Location_id);

