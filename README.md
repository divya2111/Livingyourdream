# LivingYourDream 

This is Spring Boot backend for a comprehensive travel planning application. It allows users to customized or to book default trips,manage transport and stays bookings, handle payment and they can also get checklist according to the trip - all through REST APIs.

## Tech Stack

- **Language** : Java 17+
- **Framework** : Spring Boot
- **Database** : MySQL
- **ORM** : Hibernate (JPA)
- **Build Tool** : Maven

## Module Implemented

### Admin & User
- Register/Login
- Role-based Access

### Trip Plans

- **DefaultTripPlan** : Preplanned by Admin (with transport + stays)
- **CustomTripPlan** : Planned by User (customize as their requirements)

### Transport & Seats

- Add vehicles (bus/train/flight) by mode
- Add seats per transport
- Select seats while planning/booking

### Stay & Stay Booking

- Add stays with price
- Stay booking with date-range
- Cost calculated by stay type and nights

### Booking and Payment

- Book a trip (Custom/Default)
- Total cost auto-calculated (transport + stay)
- status transition 
- Payment entity mapped to booking

### Checklist

- Auto-generated post booking
- Suggests according to the trip


## Project Structure

```bash

src/
 |--- config/
 |--- controller/
 |--- service/
 |--- model/
 |--- repository/
 |--- dto/
 |--- enum/
 |___ LivingyourdreamsApplication.java

```
## How to Run

1. Clone the repo
2. Configure DB in application.properties
 ```bash
spring.datasource.url=jdbc:mysql://localhost:3306/livingyourdream
spring.datasource.username=root
spring.datasource.password=yourpassword
```
3. Run the application
 - Using IDE: Run
   LivingyourdreamApplicatiom
 - Or via terminal:
 ```bash
 mvn spring-boot:run
 ```
4. Test APIs using Postman or Swagger

### Sample APIs

```bash
  POST /register/user
  POST /register/admin
 ```

#### Admin

```bash
  POST /places
  POST /defaultplans
  POST / transport
  POST / transport/{transportid}/seat/bulk
  POST /stay
  POST /stay/{stayId}/book
 ```

#### User

```bash
  GET  /places
  GET /defaultplans
  POST /customplans
  GET /customplans
  GET /transport
  GET /stay
  POST /bookings
  POST /Payment?bookingId={bookingId}
  POST /checklist/create/{bookingId}
 ```
 