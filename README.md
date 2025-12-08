# JobLuu - Backend API 🚀

> A robust RESTful API for the JobLuu job portal platform. Built with Spring Boot, MongoDB, and JWT authentication.

**Live API:** [https://jobluubackend.onrender.com](https://jobluubackend.onrender.com)  
**GitHub Repository:** [https://github.com/Mohmmed-Zaid/JobluuBackend](https://github.com/Mohmmed-Zaid/JobluuBackend)

---

## 📑 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Running the Application](#-running-the-application)
- [API Documentation](#-api-documentation)
- [Database Schema](#-database-schema)
- [Security](#-security)
- [Error Handling](#-error-handling)
- [Deployment](#-deployment)
- [Contributing](#-contributing)
- [License](#-license)

---

## ✨ Features

### Authentication & Authorization
- 🔐 JWT-based authentication
- 🔑 Email/Password login
- 🌐 Google OAuth 2.0 integration
- 🔄 Token refresh mechanism
- 🔒 Role-based access control (APPLICANT/EMPLOYER)

### User Management
- 👤 User registration and profile management
- 📧 Email verification with OTP
- 🔑 Password reset functionality
- 👥 Account type separation (Applicant/Employer)

### Job Management
- 📋 Create, read, update, delete job postings
- 🔍 Job search and filtering
- 🏢 Company logo upload
- 📊 Job status management
- 🎯 Job categorization

### Profile Management
- 📄 User profile CRUD operations
- 💼 Work experience tracking
- 🎓 Certifications management
- 🛠️ Skills listing

### Notifications
- 🔔 Real-time notification system
- 📬 Unread notification tracking
- ✅ Mark as read functionality
- 🗑️ Notification management

### Additional Features
- ✅ Input validation
- 🌐 CORS configuration
- 📧 Email service integration
- 🔄 Auto-incrementing ID generation
- 🏥 Health check endpoint

---

## 🛠 Tech Stack

### Core Framework
- **Spring Boot 3.x** - Application framework
- **Java 17+** - Programming language
- **Maven** - Dependency management

### Database
- **MongoDB** - NoSQL database
- **Spring Data MongoDB** - Data access layer

### Security
- **Spring Security** - Authentication & authorization
- **JWT (JSON Web Tokens)** - Stateless authentication
- **Google OAuth 2.0** - Third-party authentication

### Utilities
- **Lombok** - Reduce boilerplate code
- **Jakarta Validation** - Input validation
- **JavaMail API** - Email functionality

### Development Tools
- **Spring Boot DevTools** - Development utilities
- **SLF4J & Logback** - Logging

---

## 📁 Project Structure

```
JobluuBackend/
├── src/
│   ├── main/
│   │   ├── java/com/Cubix/Jobluu/
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java           # Security configuration
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java           # Authentication endpoints
│   │   │   │   ├── GoogleAuthController.java     # Google OAuth endpoints
│   │   │   │   ├── JobController.java            # Job management endpoints
│   │   │   │   ├── UserController.java           # User management endpoints
│   │   │   │   ├── ProfileController.java        # Profile endpoints
│   │   │   │   ├── NotificationController.java   # Notification endpoints
│   │   │   │   └── HealthController.java         # Health check endpoint
│   │   │   ├── dto/
│   │   │   │   ├── UserDto.java                  # User data transfer object
│   │   │   │   ├── LoginDto.java                 # Login request DTO
│   │   │   │   ├── JobDTO.java                   # Job data transfer object
│   │   │   │   ├── ProfileDto.java               # Profile DTO
│   │   │   │   ├── NotificationDTO.java          # Notification DTO
│   │   │   │   ├── AccountType.java              # User account type enum
│   │   │   │   ├── ApplicationStatus.java        # Application status enum
│   │   │   │   ├── JobStatus.java                # Job status enum
│   │   │   │   ├── NotificationStatus.java       # Notification status enum
│   │   │   │   ├── Experience.java               # Work experience DTO
│   │   │   │   ├── Certifications.java           # Certifications DTO
│   │   │   │   ├── Applicant.java                # Applicant DTO
│   │   │   │   ├── GoogleLoginRequest.java       # Google login request
│   │   │   │   └── ResponseDto.java              # Generic response DTO
│   │   │   ├── entities/
│   │   │   │   ├── User.java                     # User entity
│   │   │   │   ├── Job.java                      # Job entity
│   │   │   │   ├── Profile.java                  # Profile entity
│   │   │   │   ├── Notification.java             # Notification entity
│   │   │   │   ├── OTP.java                      # OTP entity
│   │   │   │   ├── Sequence.java                 # Sequence entity
│   │   │   │   └── DatabaseSequence.java         # Database sequence
│   │   │   ├── exception/
│   │   │   │   └── JobluuException.java          # Custom exception
│   │   │   ├── jwt/
│   │   │   │   ├── JwtHelper.java                # JWT utility class
│   │   │   │   ├── JwtAuthenticationFilter.java  # JWT filter
│   │   │   │   ├── MyUserDetailsService.java     # User details service
│   │   │   │   ├── CustomUserDetails.java        # Custom user details
│   │   │   │   ├── AuthenticationRequest.java    # Auth request DTO
│   │   │   │   └── AuthenticationResponse.java   # Auth response DTO
│   │   │   ├── repositories/
│   │   │   │   ├── UserRepository.java           # User data access
│   │   │   │   ├── JobRepository.java            # Job data access
│   │   │   │   ├── ProfileRepository.java        # Profile data access
│   │   │   │   ├── NotificationRepository.java   # Notification data access
│   │   │   │   └── OTPRepository.java            # OTP data access
│   │   │   ├── service/
│   │   │   │   ├── UserService.java              # User service interface
│   │   │   │   ├── UserServiceImpl.java          # User service implementation
│   │   │   │   ├── JobService.java               # Job service interface
│   │   │   │   ├── JobServiceImpl.java           # Job service implementation
│   │   │   │   ├── ProfileService.java           # Profile service interface
│   │   │   │   ├── ProfileServiceImpl.java       # Profile service implementation
│   │   │   │   ├── NotificationService.java      # Notification service interface
│   │   │   │   ├── NotificationServiceImpl.java  # Notification service implementation
│   │   │   │   └── SequenceGeneratorService.java # Auto-increment service
│   │   │   ├── utility/
│   │   │   │   ├── ErrorInfo.java                # Error information
│   │   │   │   ├── ExceptionControllerAdvice.java # Global exception handler
│   │   │   │   ├── Utilities.java                # Utility methods
│   │   │   │   └── Data.java                     # Data constants
│   │   │   └── JobluuApplication.java            # Main application class
│   │   └── resources/
│   │       ├── application.properties            # Application configuration
│   │       └── ValidationMessages.properties     # Validation messages
│   └── test/
│       └── java/com/Cubix/Jobluu/                # Test files
├── .gitignore
├── .gitattributes
├── Dockerfile                                     # Docker configuration
├── mvnw                                           # Maven wrapper (Unix)
├── mvnw.cmd                                       # Maven wrapper (Windows)
├── pom.xml                                        # Maven dependencies
└── README.md
```

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.8+**
- **MongoDB Atlas account** (or local MongoDB instance)
- **Gmail account** (for email functionality)
- **Google Cloud Console project** (for OAuth)
- **Git**

---

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/Mohmmed-Zaid/JobluuBackend.git
cd JobluuBackend
```

### 2. Install Dependencies

```bash
mvn clean install
```

Or if you're using the Maven wrapper:

```bash
./mvnw clean install   # Unix/Linux/Mac
mvnw.cmd clean install # Windows
```

---

## ⚙️ Configuration

### 1. MongoDB Setup

Create a MongoDB Atlas cluster or use a local MongoDB instance.

### 2. Gmail App Password

1. Enable 2-Factor Authentication on your Gmail account
2. Generate an App Password: [Google Account Settings](https://myaccount.google.com/apppasswords)
3. Use this app password in `application.properties`

### 3. Google OAuth Setup

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select existing
3. Enable Google+ API
4. Create OAuth 2.0 credentials
5. Add authorized origins and redirect URIs
6. Copy the Client ID

### 4. Configure application.properties

Update `src/main/resources/application.properties`:

```properties
# Application Name
spring.application.name=Jobluu

# MongoDB Configuration
spring.data.mongodb.uri=mongodb+srv://YOUR_USERNAME:YOUR_PASSWORD@YOUR_CLUSTER.mongodb.net/jobluuDB?retryWrites=true&w=majority
spring.data.mongodb.auto-index-creation=true

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Google OAuth Configuration
google.client-id=YOUR_GOOGLE_CLIENT_ID

# JWT Configuration
jwt.secret=yourSuperSecretKeyForJWTGeneration1234567890
jwt.expiration=3600000

# Custom Error Messages
USER_FOUND=User has already registered!
USER_NOT_FOUND=User is not registered.
INVALID_CREDENTIAL=Invalid Credential.
OTP_NOT_FOUND=OTP is expired.
OTP_INCORRECT=Incorrect OTP.
EMAIL_REQUIRED=Enter Your Email
PASSWORD_REQUIRED=Password Required
PASSWORD_TOO_SHORT=Password Is Too Short
PASSWORD_CHANGED_SUCCESSFULLY=Password Changed Successfully
NEW_PASSWORD_SAME_AS_CURRENT=Same Password
JOB_NOT_FOUND=Job Not Found!
```

### 5. Environment Variables (Recommended for Production)

Instead of hardcoding sensitive data, use environment variables:

```bash
export MONGODB_URI="mongodb+srv://..."
export GOOGLE_CLIENT_ID="your-client-id"
export MAIL_USERNAME="your-email@gmail.com"
export MAIL_PASSWORD="your-app-password"
export JWT_SECRET="your-jwt-secret"
```

Update `application.properties` to use them:

```properties
spring.data.mongodb.uri=${MONGODB_URI}
google.client-id=${GOOGLE_CLIENT_ID}
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
jwt.secret=${JWT_SECRET}
```

---

## 🏃 Running the Application

### Development Mode

```bash
mvn spring-boot:run
```

Or using Maven wrapper:

```bash
./mvnw spring-boot:run   # Unix/Linux/Mac
mvnw.cmd spring-boot:run # Windows
```

The application will start on `http://localhost:8080`

### Production Build

```bash
mvn clean package
java -jar target/Jobluu-0.0.1-SNAPSHOT.jar
```

### Using Docker

```bash
# Build Docker image
docker build -t jobluu-backend .

# Run container
docker run -p 8080:8080 \
  -e MONGODB_URI="your-mongodb-uri" \
  -e GOOGLE_CLIENT_ID="your-client-id" \
  jobluu-backend
```

---

## 📚 API Documentation

### Base URL
```
Production: https://jobluubackend.onrender.com
Local: http://localhost:8080
```

### Authentication Endpoints

#### POST `/api/auth/login`
Login with email and password

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "user@example.com",
    "accountType": "APPLICANT"
  },
  "message": "Login successful"
}
```

#### POST `/api/auth/google`
Google OAuth login

**Request Body:**
```json
{
  "credential": "google-id-token",
  "accountType": "APPLICANT"
}
```

#### POST `/api/auth/validate`
Validate JWT token

**Headers:**
```
Authorization: Bearer <token>
```

#### GET `/api/auth/me`
Get current user details

**Headers:**
```
Authorization: Bearer <token>
```

#### POST `/api/auth/refresh`
Refresh JWT token

**Headers:**
```
Authorization: Bearer <token>
```

---

### User Endpoints

#### POST `/api/users/register`
Register new user

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "SecurePass123!",
  "confirmpassword": "SecurePass123!",
  "accountType": "APPLICANT"
}
```

#### POST `/api/users/login`
User login (alternative endpoint)

**Request Body:**
```json
{
  "email": "john@example.com",
  "password": "SecurePass123!"
}
```

#### POST `/api/users/sendOTP/{email}`
Send OTP for password reset

**Path Parameter:** `email` - User's email address

#### GET `/api/users/verifyOtp/{email}/{otp}`
Verify OTP

**Path Parameters:**
- `email` - User's email
- `otp` - 6-digit OTP code

#### POST `/api/users/changePass`
Change user password

**Request Body:**
```json
{
  "email": "john@example.com",
  "password": "NewPassword123!"
}
```

---

### Job Endpoints

All job endpoints require authentication.

#### POST `/jobs/post`
Create new job posting

**Headers:**
```
Authorization: Bearer <token>
```

**Request Body:**
```json
{
  "jobTitle": "Senior Java Developer",
  "description": "We are looking for...",
  "location": "Remote",
  "company": "Tech Corp",
  "packageOffered": "$80,000 - $120,000",
  "jobType": "Full-time",
  "exprience": "5+ years",
  "skillsRequired": ["Java", "Spring Boot", "MongoDB"]
}
```

#### GET `/jobs/getAll`
Get all job postings

**Headers:**
```
Authorization: Bearer <token>
```

#### GET `/jobs/{id}`
Get job by ID

**Headers:**
```
Authorization: Bearer <token>
```

**Path Parameter:** `id` - Job ID

#### PUT `/jobs/update/{id}`
Update job posting

**Headers:**
```
Authorization: Bearer <token>
```

**Path Parameter:** `id` - Job ID

**Request Body:** Same as POST `/jobs/post`

#### DELETE `/jobs/delete/{id}`
Delete job posting

**Headers:**
```
Authorization: Bearer <token>
```

**Path Parameter:** `id` - Job ID

#### POST `/jobs/create-with-logo`
Create job with company logo

**Headers:**
```
Authorization: Bearer <token>
Content-Type: multipart/form-data
```

**Form Data:**
- `job` - JSON job data
- `logo` - Image file (optional)

#### GET `/jobs/status/{status}`
Get jobs by status

**Headers:**
```
Authorization: Bearer <token>
```

**Path Parameter:** `status` - Job status

#### GET `/jobs/search?q={query}`
Search jobs

**Headers:**
```
Authorization: Bearer <token>
```

**Query Parameter:** `q` - Search query

---

### Profile Endpoints

#### GET `/api/profiles/get/{id}`
Get profile by ID

**Path Parameter:** `id` - User ID

#### PUT `/api/profiles/update`
Update profile

**Request Body:**
```json
{
  "id": 1,
  "email": "john@example.com",
  "jobTitle": "Senior Developer",
  "company": "Tech Corp",
  "location": "New York",
  "about": "Experienced developer...",
  "skills": ["Java", "Spring Boot", "React"],
  "experiences": [
    {
      "company": "Previous Co",
      "position": "Developer",
      "duration": "2 years",
      "description": "Worked on...",
      "current": false
    }
  ],
  "certifications": [
    {
      "name": "AWS Certified",
      "issuer": "Amazon",
      "date": "2023-01-15",
      "credentialId": "ABC123"
    }
  ]
}
```

#### PUT `/api/profiles/update/{id}`
Update profile by ID

**Path Parameter:** `id` - Profile ID

#### POST `/api/profiles/create`
Create new profile

**Request Body:** Same as update

---

### Notification Endpoints

#### GET `/notification/unread/{userId}`
Get unread notifications

**Path Parameter:** `userId` - User ID

#### GET `/notification/all/{userId}`
Get all notifications

**Path Parameter:** `userId` - User ID

#### GET `/notification/count/{userId}`
Get unread notification count

**Path Parameter:** `userId` - User ID

#### POST `/notification/send`
Send notification

**Request Body:**
```json
{
  "userId": 1,
  "message": "Your application has been viewed",
  "action": "APPLICATION_VIEWED",
  "route": "/applications/123",
  "title": "Application Update",
  "icon": "eye"
}
```

#### PUT `/notification/mark-read/{notificationId}`
Mark notification as read

**Path Parameter:** `notificationId` - Notification ID

#### PUT `/notification/mark-all-read/{userId}`
Mark all notifications as read

**Path Parameter:** `userId` - User ID

#### DELETE `/notification/delete/{notificationId}`
Delete notification

**Path Parameter:** `notificationId` - Notification ID

#### DELETE `/notification/delete-all/{userId}`
Delete all notifications

**Path Parameter:** `userId` - User ID

#### GET `/notification/type/{userId}/{action}`
Get notifications by type

**Path Parameters:**
- `userId` - User ID
- `action` - Notification action type

---

### Health Check Endpoint

#### GET `/api/public/health`
Check API health status

**Response:**
```
OK
```

---

## 🗄 Database Schema

### User Collection
```javascript
{
  "_id": Long,
  "name": String,
  "email": String (unique, indexed),
  "password": String (encrypted),
  "accountType": String (APPLICANT | EMPLOYER),
  "googleId": String (optional),
  "profilePicture": String (optional),
  "createdAt": Date,
  "updatedAt": Date
}
```

### Job Collection
```javascript
{
  "_id": Long,
  "jobTitle": String,
  "description": String,
  "location": String,
  "company": String,
  "packageOffered": String,
  "jobType": String,
  "exprience": String,
  "skillsRequired": [String],
  "companyLogo": String (optional),
  "postTime": Date,
  "status": String,
  "employerId": Long,
  "applicants": [Applicant]
}
```

### Profile Collection
```javascript
{
  "_id": Long,
  "userId": Long,
  "email": String,
  "jobTitle": String,
  "company": String,
  "location": String,
  "about": String,
  "skills": [String],
  "experiences": [Experience],
  "certifications": [Certification],
  "createdAt": Date,
  "updatedAt": Date
}
```

### Notification Collection
```javascript
{
  "_id": Long,
  "userId": Long,
  "message": String,
  "action": String,
  "route": String (optional),
  "status": String (UNREAD | READ),
  "timestamp": Date,
  "title": String (optional),
  "icon": String (optional)
}
```

### OTP Collection
```javascript
{
  "_id": String,
  "email": String,
  "otp": String,
  "expiryTime": Date
}
```

---

## 🔒 Security

### JWT Authentication
- Tokens expire after 1 hour (configurable)
- Tokens are signed with HS256 algorithm
- Refresh token mechanism available

### Password Security
- Passwords are hashed using BCrypt
- Minimum password requirements enforced
- Password validation on registration

### CORS Configuration
- Configured for specific origins
- Supports credentials
- Allows common HTTP methods

### Input Validation
- Jakarta Validation annotations
- Custom validation messages
- Email format validation
- OTP format validation (6 digits)

### API Security
- Protected endpoints require valid JWT
- Role-based access control
- Request/Response sanitization

---

## 🚨 Error Handling

### Custom Exception Handling

The application uses a global exception handler (`ExceptionControllerAdvice`) that catches and formats errors consistently.

**Standard Error Response:**
```json
{
  "timestamp": "2024-12-08T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "USER_FOUND: User has already registered!",
  "path": "/api/users/register"
}
```

### Common Error Codes

| Status Code | Description |
|-------------|-------------|
| 400 | Bad Request - Invalid input |
| 401 | Unauthorized - Invalid credentials |
| 403 | Forbidden - Insufficient permissions |
| 404 | Not Found - Resource doesn't exist |
| 409 | Conflict - Duplicate resource |
| 500 | Internal Server Error |

---

## 📦 Deployment

### Deploy to Render

1. **Create a new Web Service on Render**

2. **Connect your GitHub repository**

3. **Configure environment variables:**
   - `MONGODB_URI`
   - `GOOGLE_CLIENT_ID`
   - `MAIL_USERNAME`
   - `MAIL_PASSWORD`
   - `JWT_SECRET`

4. **Build Command:**
   ```bash
   mvn clean package -DskipTests
   ```

5. **Start Command:**
   ```bash
   java -jar target/Jobluu-0.0.1-SNAPSHOT.jar
   ```

### Deploy to Heroku

```bash
# Login to Heroku
heroku login

# Create new app
heroku create jobluu-backend

# Set environment variables
heroku config:set MONGODB_URI="your-mongodb-uri"
heroku config:set GOOGLE_CLIENT_ID="your-client-id"
heroku config:set JWT_SECRET="your-jwt-secret"

# Deploy
git push heroku main
```

### Deploy with Docker

```bash
# Build image
docker build -t jobluu-backend .

# Run container
docker run -p 8080:8080 \
  -e MONGODB_URI="mongodb-uri" \
  -e GOOGLE_CLIENT_ID="client-id" \
  -e JWT_SECRET="jwt-secret" \
  jobluu-backend
```

---

## 🧪 Testing

### Run Tests

```bash
mvn test
```

### Run with Coverage

```bash
mvn clean test jacoco:report
```

---

## 🤝 Contributing

We welcome contributions! Please follow these guidelines:

1. **Fork the repository**

2. **Create a feature branch**
```bash
git checkout -b feature/AmazingFeature
```

3. **Commit your changes**
```bash
git commit -m 'Add some AmazingFeature'
```

4. **Push to the branch**
```bash
git push origin feature/AmazingFeature
```

5. **Open a Pull Request**

### Code Style
- Follow Java naming conventions
- Use meaningful variable and method names
- Add comments for complex logic
- Write unit tests for new features
- Update documentation as needed

---

## 📄 License

This project is licensed under the **Apache-2.0 License**.

---

## 👥 Team

- **Mohmmed Zaid** - Lead Developer

---

## 🐛 Known Issues

- Logo upload feature needs additional testing
- OTP expiry cleanup could be optimized
- Need to add pagination for large datasets

---

## 🗺 Roadmap

- [ ] Add WebSocket support for real-time notifications
- [ ] Implement file upload service for resumes
- [ ] Add email templates for better formatting
- [ ] Implement rate limiting
- [ ] Add API documentation with Swagger/OpenAPI
- [ ] Implement caching with Redis
- [ ] Add comprehensive integration tests
- [ ] Implement job application tracking
- [ ] Add analytics endpoints
- [ ] Implement background job processing

---

## 📞 Support

For support and questions:
- **GitHub Issues:** [Create an issue](https://github.com/Mohmmed-Zaid/JobluuBackend/issues)
- **Email:** Contact through GitHub profile

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- MongoDB for the flexible database
- Google for OAuth 2.0 services
- All contributors and testers

---

**Made with ❤️ by MZ**
