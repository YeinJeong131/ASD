# Table of Contents for ASD group
1. [Appearance Settings & Notes/Highlights Feature](#yein-features)

------

<h1 id="yein-features">Appearance Settings & Notes/Highlights Feature</h1>

**Branch:** `appearance-note-feature`  
**Developer:** Yein Jeong  
**Student ID:** [Your Student ID]  
**Features:** 
- Feature 5: Enhance User Experience (Appearance Customization)
- Feature 6: Highlight & Note System (My Notes Page)

---

## Table of Contents (Yein)
1. [Feature Overview](#fo)
2. [File Structure](#file-structure)
3. [Feature 1: Appearance Settings](#feature-1-appearance-settings)
4. [Feature 2: Highlight & Notes](#feature-2-highlight--notes)
5. [Integration Points](#integration-points)
6. [Database Schema](#database-schema)
7. [API Documentation](#api-documentation)
8. [How to Run](#htr)
9. [Testing](#testing)
10. [Assignment Requirements Checklist](#assignment-requirements-checklist)

---

<h2 id="fo">Feature Overview</h2> 

This branch implements two main features:

### Feature 1: Appearance Settings
Users can customize the appearance of Betterpedia through a dedicated settings page. When logged in, preferences are stored and automatically applied on future visits.

**Available Options:**
- Light/Dark mode
- Font size (small, medium, large, extra-large)
- Font style (Arial, Serif, Sans-serif, Monospace)
- Line spacing (compact, normal, relaxed)
- Page width (fixed or wide)
- Date format (DD/MM/YYYY, MM/DD/YYYY, YYYY-MM-DD)
- Time offset (UTC -12 to +14)

### Feature 2: Highlight & Notes
Users can highlight specific sentences on wiki pages and add personal notes. All highlights and notes are saved to a personal "My Notes" page with quick access links to original pages.

**Available Options:**
- Text highlighting with multiple colors (yellow, blue, green, red, purple)
- Add personal notes to highlighted text
- View all notes organized by page or chronologically
- Edit and delete notes
- Restore highlights on original pages

---

## 📁 File Structure

### Backend (Java)
```
src/main/java/betterpedia/
├── appearance/
│   ├── controller/
│   │   ├── UserSettingController.java          # REST API endpoints for settings
│   │   └── UserSettingPageController.java      # Settings page routing
│   ├── entity/
│   │   └── UserSettings.java                   # Settings entity/model
│   ├── repository/
│   │   └── UserSettingsRepository.java         # JPA repository for database
│   └── service/
│       └── UserSettingService.java             # Business logic & validation
│
├── notes/
│   ├── controller/
│   │   ├── NoteController.java                 # REST API endpoints for notes
│   │   └── MyNotesController.java              # My Notes page routing
│   ├── entity/
│   │   └── Note.java                           # Note entity/model
│   ├── repository/
│   │   └── NoteRepository.java                 # JPA repository for database
│   └── service/
│       └── NoteService.java                    # Business logic
│
├── wiki/
│   └── documentController.java                 # Wiki pages (integration with team)
│
├── user/
│   └── F101UserManagementController.java       # User management (integration with Esha)
│
└── BetterpediaApplication.java                 # Spring Boot main application
```

### Frontend
```
src/main/resources/
├── static/
│   ├── css/
│   │   ├── common-settings.css                 # Global theme variables
│   │   ├── settings.css                        # Settings page styles
│   │   ├── my-notes.css                        # My Notes page styles
│   │   └── yein-highlight-system.css           # Highlight system styles
│   └── js/
│       ├── apply-settings.js                   # Global settings application
│       ├── settings.js                         # Settings page functionality
│       ├── my-notes.js                         # My Notes page functionality
│       └── yein-highlight-system.js            # Highlight system core logic
│
└── templates/
    ├── settings_page.html                      # Appearance settings page
    ├── notes/
    │   └── my-notes.html                       # My Notes list page
    ├── wiki/
    │   ├── home.html                           # Wiki home page (integration)
    │   └── document.html                       # Wiki document with highlights
    ├── login.html                              # Login page (integration)
    ├── account-settings.html                   # Account page (integration)
    └── admin.html                              # Admin page (integration)
```

### Tests
```
src/test/java/betterpedia/
├── appearance/
│   ├── controller/
│   │   └── UserSettingsControllerTest.java     # API endpoint tests
│   └── service/
│       └── UserSettingsServiceTest.java        # Service layer tests
│
└── notes/
    ├── controller/
    │   └── NoteControllerTest.java             # API endpoint tests
    └── service/
        └── NoteServiceTest.java                # Service layer tests
```

---

## 🎨 Feature 1: Appearance Settings

### Backend Components

#### 1. **UserSettings Entity** (`entity/UserSettings.java`)
```java
// Database table: user_settings
// Fields: id, userId, darkMode, fontSize, dateFormat, timeOffset, 
//         pageWidth, fontStyle, lineSpacing
```

**Key Features:**
- Stores user appearance preferences
- Default values provided for new users
- Linked to user by userId

#### 2. **UserSettingsRepository** (`repository/UserSettingsRepository.java`)
```java
// JPA Repository for database operations
// Custom methods:
- findByUserId(Long userId)           // Get user's settings
- existsByUserId(Long userId)         // Check if settings exist
```

#### 3. **UserSettingService** (`service/UserSettingService.java`)
```java
// Business logic methods:
- getUserSettings(userId)             // Get or create default settings
- saveUserSettings(settings)          // Save/update with validation
- createDefaultSettings(userId)       // Generate default settings
- validateSettings(settings)          // Validate input values
```

**Validation Rules:**
- Font size: small, medium, large, extra-large
- Date format: DD/MM/YYYY, MM/DD/YYYY, YYYY-MM-DD
- Page width: fixed, wide
- Font style: arial, serif, sans-serif, monospace
- Line spacing: compact, normal, relaxed
- Time offset: -12 to +14 hours

#### 4. **Controllers**

**UserSettingController** (REST API - `/api/settings`)
- `GET /api/settings/{userId}` - Retrieve user settings
- `POST /api/settings` - Save user settings

**UserSettingPageController** (Page Routing)
- `GET /settings` - Settings page (requires login)
- `GET /test-login` - Mock login for testing

### Frontend Components

#### 1. **Settings Page** (`templates/settings_page.html`)
User interface with:
- Theme toggle (Dark mode)
- Typography controls (Font size, style, line spacing)
- Layout options (Page width)
- Date & Time settings (Format, timezone)
- Live preview section
- Save/Reset buttons

#### 2. **Settings JavaScript** (`static/js/settings.js`)
**Core Functions:**
```javascript
- loadCurrentSettings()          // Load from server
- applyAllSettings()             // Apply all current settings
- applyDarkMode()                // Toggle dark/light theme
- applyFontSize()                // Change font size
- applyFontStyle()               // Change font family
- applyLineSpacing()             // Adjust line height
- applyPageWidth()               // Fixed/wide layout
- updateDatePreview()            // Show date format example
- saveSettings()                 // POST to server
- resetToDefaults()              // Reset all settings
```

#### 3. **Global Settings Application** (`static/js/apply-settings.js`)
Applies saved settings globally across all pages:
```javascript
- Runs on every page load
- Fetches user settings from API
- Applies CSS variables for themes
- Updates body classes for typography
```

#### 4. **CSS Styling**
- `settings.css` - Settings page specific styles
- `common-settings.css` - Global theme variables and classes

**CSS Variable System:**
```css
/* Dark mode variables */
--bg-color, --text-color, --card-bg, --border-color

/* Font size classes */
.font-small, .font-medium, .font-large, .font-extra-large

/* Font style classes */
.font-arial, .font-serif, .font-sans-serif, .font-monospace

/* Line spacing classes */
.line-compact, .line-normal, .line-relaxed
```

---

## 📝 Feature 2: Highlight & Notes

### Backend Components

#### 1. **Note Entity** (`entity/Note.java`)
```java
// Database table: notes
// Fields: noteId, userId, pageUrl, highlightedText, noteContent,
//         position, createdDate, updatedDate, highlightColour
```

**Key Features:**
- Stores highlighted text and personal notes
- Tracks creation and update timestamps
- Position data stored as JSON string
- Supports multiple highlight colors

#### 2. **NoteRepository** (`repository/NoteRepository.java`)
```java
// Custom query methods:
- findByUserIdOrderByCreatedDateDesc(userId)
- findByUserIdAndPageUrlOrderByCreatedDateDesc(userId, pageUrl)
- findByNoteIdAndUserId(noteId, userId)
```

#### 3. **NoteService** (`service/NoteService.java`)
```java
// Business logic methods:
- saveNote(...)                   // Create new note
- getAllNotesByUser(userId)       // Get all user notes
- getNotesByUserAndPage(...)      // Get notes for specific page
- updateNote(...)                 // Update note content/color
- deleteNote(noteId, userId)      // Delete note
```

#### 4. **Controllers**

**NoteController** (REST API - `/api/notes`)
- `POST /api/notes` - Create new note with highlight
- `GET /api/notes` - Get all user notes
- `GET /api/notes/page?url={url}` - Get notes for specific page
- `PUT /api/notes/{noteId}` - Update note
- `DELETE /api/notes/{noteId}` - Delete note

**MyNotesController** (Page Routing)
- `GET /my-notes` - My Notes list page

### Frontend Components

#### 1. **Highlight System** (`static/js/yein-highlight-system.js`)

**YeinHighlightSystem Class:**
```javascript
// Main Features:
- init()                          // Initialize system
- createHighlight(color)          // Create highlight on selected text
- addNote()                       // Add note to highlight
- deleteHighlight(id)             // Remove highlight
- loadExistingHighlights()        // Restore saved highlights
- saveToServer(data)              // Persist to database
- showTooltip(event)              // Show color selection tooltip
```

**User Workflow:**
1. User selects text on wiki page
2. Tooltip appears with color options
3. User clicks color or "Add Note"
4. Highlight is created and saved to server
5. Data stored in database for future retrieval

**Global API:**
```javascript
window.YeinHighlightSystem.getInstance()
window.YeinHighlightSystem.createHighlight(color)
window.YeinHighlightSystem.getHighlights()
window.YeinHighlightSystem.clearAll()
window.YeinHighlightSystem.getStats()
```

#### 2. **My Notes Page** (`templates/notes/my-notes.html`)

**Features:**
- Two view modes: "All Notes" and "By Page"
- Note cards showing:
  - Highlighted text
  - Personal note content
  - Page URL (clickable link)
  - Creation date
  - Highlight color badge
  - Edit/Delete buttons
- Sidebar navigation
- Empty state message

#### 3. **My Notes JavaScript** (`static/js/my-notes.js`)
```javascript
// Core Functions:
- toggleView()                    // Switch between view modes
- editNote(noteId)                // Open edit modal
- saveNoteChanges()               // Update note via API
- deleteNote(noteId)              // Delete note with confirmation
- updateNoteCardInDOM(...)        // Update UI after changes
- updateNoteCounts()              // Refresh note counts
```

#### 4. **CSS Styling**
- `yein-highlight-system.css` - Highlight colors, tooltip styles
- `my-notes.css` - My Notes page layout and cards

**Highlight Color System:**
```css
.yein-highlight.yellow { background: #ffeb3b; }
.yein-highlight.blue   { background: #2196f3; }
.yein-highlight.green  { background: #4caf50; }
.yein-highlight.red    { background: #f44336; }
.yein-highlight.purple { background: #9c27b0; }
```

---

## 🔗 Integration Points

### Integration with User Management Feature (Esha)
- **User Session:** Uses `session.userId` and `session.username` from Esha's authentication
- **Controllers Connected:**
  - `F101UserManagementController.java` provides login, account, and admin pages
  - User settings linked by `userId` foreign key
  - Notes linked by `userId` foreign key

### Integration with Wiki Document Feature
- **Document Pages:** Highlight system active on `/wiki/{documentId}` pages
- **documentController.java** provides wiki content
- **document.html** includes highlight system JavaScript
- Notes store `pageUrl` reference to wiki pages

### Integration with Home Page
- **home.html** provides navigation to all features
- Links to Settings, My Notes, and Wiki pages
- Consistent navbar across all pages

### Session Management
```java
// Current implementation uses mock session:
session.setAttribute("userId", 1L);
session.setAttribute("username", "TestUser");

// Will integrate with Esha's authentication system:
// - Real login via F101UserManagementController
// - Session management
// - Access control
```

---

<h2 id="htr">How to Run</h2>

### Prerequisites
- Java 17+
- Gradle
- Spring Boot 3.x
- H2/PostgreSQL Database

### Setup Steps

1. **Clone the repository and checkout branch:**
```bash
git checkout appearance-note-feature
```

2. **Configure database** (in `application.properties`):
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=update
```

3. **Build and run:**
```bash
./gradlew bootRun
```

4. **Access the application:**
- Settings Page: `http://localhost:8080/settings`
- My Notes Page: `http://localhost:8080/my-notes`
- Wiki Home: `http://localhost:8080/wiki`
- Wiki Document: `http://localhost:8080/wiki/spring-framework`

### Test Login
For testing purposes, visit: `http://localhost:8080/test-login`
This creates a mock session with userId=1.

---

## 🧪 Testing

### Automated Tests

Located in `src/test/java/betterpedia/`:

#### Appearance Settings Tests
**UserSettingsControllerTest:**
- Test GET settings endpoint
- Test POST settings with valid data
- Test validation errors

**UserSettingsServiceTest:**
- Test settings retrieval
- Test default settings creation
- Test validation rules
- Test settings update logic

#### Notes & Highlights Tests
**NoteControllerTest:**
- Test create note endpoint
- Test get notes by user
- Test get notes by page
- Test update note
- Test delete note

**NoteServiceTest:**
- Test note creation
- Test note retrieval
- Test note updates
- Test note deletion

### Manual Testing Checklist

#### Appearance Settings:
- [ ] Change dark mode and verify theme switches
- [ ] Change font size and verify text scales
- [ ] Change font style and verify font family changes
- [ ] Change line spacing and verify spacing adjusts
- [ ] Change page width and verify layout changes
- [ ] Change date format and verify preview updates
- [ ] Save settings and reload page - settings persist
- [ ] Reset to defaults works correctly

#### Highlight & Notes:
- [ ] Select text on wiki page - tooltip appears
- [ ] Create yellow highlight - text is highlighted
- [ ] Create blue, green highlights - colors work
- [ ] Add note with highlight - note saves
- [ ] Click existing highlight - edit tooltip appears
- [ ] Edit note content - changes save
- [ ] Change highlight color - color updates
- [ ] Delete note - note removed
- [ ] Navigate to My Notes page - all notes displayed
- [ ] Toggle between "All Notes" and "By Page" views
- [ ] Click page link - returns to original wiki page

### API Testing Examples

**Test Settings API:**
```bash
# Get user settings
curl http://localhost:8080/api/settings/1

# Save settings
curl -X POST http://localhost:8080/api/settings \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "darkMode": true,
    "fontSize": "large",
    "fontStyle": "serif",
    "lineSpacing": "relaxed",
    "pageWidth": "wide",
    "dateFormat": "YYYY-MM-DD",
    "timeOffset": 9
  }'
```

**Test Notes API:**
```bash
# Create note
curl -X POST http://localhost:8080/api/notes \
  -F "pageUrl=/wiki/spring-framework" \
  -F "highlightedText=Spring Framework is awesome" \
  -F "noteContent=Remember this for exam" \
  -F "highlightColor=yellow"

# Get all notes
curl http://localhost:8080/api/notes

# Get notes for specific page
curl "http://localhost:8080/api/notes/page?url=/wiki/spring-framework"

# Delete note
curl -X DELETE http://localhost:8080/api/notes/1
```

---

## 📊 Database Schema

### user_settings Table
```sql
CREATE TABLE user_settings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    dark_mode BOOLEAN DEFAULT FALSE,
    font_size VARCHAR(20) DEFAULT 'medium',
    date_format VARCHAR(20) DEFAULT 'DD/MM/YYYY',
    time_offset INTEGER DEFAULT 0,
    page_width VARCHAR(10) DEFAULT 'fixed',
    font_style VARCHAR(20) DEFAULT 'arial',
    line_spacing VARCHAR(20) DEFAULT 'normal'
);
```

### notes Table
```sql
CREATE TABLE notes (
    note_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    page_url VARCHAR(500) NOT NULL,
    highlighted_text TEXT NOT NULL,
    note_content TEXT,
    position TEXT,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP NOT NULL,
    highlight_colour VARCHAR(20) DEFAULT 'yellow'
);
```

---

## 🎯 Features Delivered

### ✅ Assignment Requirements Met:

1. **Software Requirements (2 Marks)**
   - User stories documented for appearance settings
   - User stories documented for highlight & notes
   - Requirements backlog maintained

2. **Solution Architecture (2 Marks)**
   - MVC pattern implemented
   - Service layer for business logic
   - Repository layer for data access
   - RESTful API design

3. **Solution Design (2 Marks)**
   - User journey maps created
   - Database design with relationships
   - Component interaction design

4. **Working Software Demo (10 Marks)**
   - ✅ Demo meets all feature requirements
   - ✅ Clear and consistent UI/UX
   - ✅ Input validation and error handling
   - ✅ Reusable code (services, APIs, components)
   - ✅ Clean, commented, consistent code style

5. **Automated Testing (4 Marks)**
   - Unit tests for services
   - Controller tests for APIs
   - Test coverage for core functionality

---

## 👤 Developer Information

**Name:** Yein Jeong  
**Features:** 
1. Enhance User Experience (Appearance Settings)
2. Highlight & Note System

**Branch:** `appearance-note-feature`  
**Integration:** Connected with User Management (Esha), Wiki Document features

---

## 📝 Notes

- Current implementation uses mock user session (userId=1)
- Will integrate with Esha's authentication system for final release
- Highlight restoration on original pages is partially implemented
- Future enhancement: Export notes to PDF/Word format
