CREATE TABLE Client (
	ID_client int IDENTITY(1,1) NOT NULL,
	Name varchar(100) NOT NULL UNIQUE,
	Longitude  varchar(50) NOT NULL,
	Latitude varchar(50) NOT NULL,
	Status int NOT NULL,
	Location_Id int NOT NULL,
  CONSTRAINT PK_CLIENT PRIMARY KEY (ID_client)
  
);

CREATE TABLE Location (
	Location_id int IDENTITY(1,1) NOT NULL,
	Name varchar(70) NOT NULL,
  CONSTRAINT PK_LOCATION PRIMARY KEY (Location_id)
  
);

CREATE TABLE Events (
	Event_id int IDENTITY(1,1) NOT NULL,
	Name varchar(100) NOT NULL,
	Description varchar(100),
  CONSTRAINT PK_EVENTS PRIMARY KEY (Event_id)
  
);

CREATE TABLE EventLocation (
	Event_id integer NOT NULL,
	Location_id integer NOT NULL
)
ALTER TABLE [Client] WITH CHECK ADD CONSTRAINT [Client_fk0] FOREIGN KEY ([Location_Id]) REFERENCES [Location]([Location_id])
GO
ALTER TABLE [Client] CHECK CONSTRAINT [Client_fk0]
GO




ALTER TABLE [EventLocation] WITH CHECK ADD CONSTRAINT [EventLocation_fk0] FOREIGN KEY ([Event_id]) REFERENCES [Events]([Event_id])

GO
ALTER TABLE [EventLocation] CHECK CONSTRAINT [EventLocation_fk0]
GO
ALTER TABLE [EventLocation] WITH CHECK ADD CONSTRAINT [EventLocation_fk1] FOREIGN KEY ([Location_id]) REFERENCES [Location]([Location_id])

GO
ALTER TABLE [EventLocation] CHECK CONSTRAINT [EventLocation_fk1]
GO

