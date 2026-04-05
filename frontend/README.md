# AstroLog Frontend

React web application for the AstroLog star chart observation logger.

**Status**: ✅ Complete and Ready for Deployment
**Node.js Version**: v24.14.1 ✅ Installed
**npm Version**: 11.11.0 ✅ Installed
**Backend Status**: ✅ Running on port 8080

## Architecture

### Component Structure

```
App (Main component)
├── StarChartForm (Input form)
├── StarChartDisplay (Chart viewer)
└── ObservationLog (History table)
```

### State Management

Uses React Context API with `ObservationContext`:
- Observations list
- Current/selected observation
- Loading states
- Error messages

### Key Files

- `App.jsx` - Main application component
- `main.jsx` - React entry point
- `components/StarChartForm.jsx` - Form for generating charts
- `components/StarChartDisplay.jsx` - Chart display component
- `components/ObservationLog.jsx` - Observation history/log
- `context/ObservationContext.jsx` - State management
- `services/starChartAPI.js` - API client
- `styles/` - CSS stylesheets

## Setup & Installation

### Prerequisites
- Node.js v24.14.1 ✅ Installed
- npm 11.11.0 ✅ Installed

### Installation

1. Navigate to frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Create `.env` file (optional):
```
VITE_API_BASE_URL=http://localhost:8080/api
```

## Development

Start development server:
```bash
npm run dev
```

Server runs on `http://localhost:3000` with hot module replacement.

## Features

### StarChartForm Component

Allows users to input:
- **Location** - Name and coordinates (latitude/longitude)
- **Date/Time** - Observation date and time
- **Target** - Constellation or RA/Dec coordinates
- **Notes** - Optional observation notes
- **Download Image** - Checkbox to save image locally

Features:
- Form validation with user-friendly error messages
- Real-time error clearing
- Submit button loading state
- Success message display
- Form reset after submission

### StarChartDisplay Component

Shows generated star chart:
- Observation details summary
- Chart image display
- Link to full resolution chart
- Storage information (if image downloaded)
- Error handling for failed chart loads

### ObservationLog Component

Displays observation history:
- Table of all observations
- Search/filter by location or target
- Sort by creation date (newest first)
- Click to view observation details
- Delete observations with confirmation
- Responsive design

## API Integration

### starChartAPI.js

Axios-based API client with methods:
- `generateStarChart(data)` - POST request
- `getAllObservations()` - GET all
- `getObservationById(id)` - GET single
- `getObservationsByLocation(location)` - GET filtered
- `getObservationsByTarget(target)` - GET filtered
- `updateObservation(id, data)` - PUT request
- `deleteObservation(id)` - DELETE request
- `checkHealth()` - Health check

Features:
- Centralized configuration
- Response error handling
- Automatic error logging

## Styling

### CSS Architecture

Utility classes and component-specific stylesheets:
- `index.css` - Global styles and CSS variables
- `App.css` - Main layout
- `styles/StarChartForm.css` - Form styling
- `styles/StarChartDisplay.css` - Display styling
- `styles/ObservationLog.css` - Table and filter styling

### Color Scheme

CSS custom properties defined in `index.css`:
- Primary: `#00d4ff` (Cyan)
- Secondary: `#ff6b9d` (Pink)
- Accent: `#ffd700` (Gold)
- Dark backgrounds with light text
- Smooth transitions and hover effects

### Responsive Design

Media queries for:
- Desktop (1024px+)
- Tablet (768px - 1024px)
- Mobile (<768px)

## Building for Production

Build optimized bundle:
```bash
npm run build
```

Output: `dist/` folder

Preview production build:
```bash
npm run preview
```

## Deployment

### Static Hosting

1. Build the project:
```bash
npm run build
```

2. Deploy `dist/` folder to:
- Vercel
- Netlify
- AWS S3 + CloudFront
- GitHub Pages
- Any static host

### Docker Deployment

Create `Dockerfile`:
```dockerfile
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM node:18-alpine
WORKDIR /app
RUN npm install -g serve
COPY --from=build /app/dist ./dist
EXPOSE 3000
CMD ["serve", "-s", "dist", "-l", "3000"]
```

Build and run:
```bash
docker build -t astrolog-frontend .
docker run -p 3000:3000 astrolog-frontend
```

## Configuration

### Environment Variables

Create `.env` file in frontend root:
```
VITE_API_BASE_URL=http://localhost:8080/api
VITE_LOG_LEVEL=debug
```

Access in components:
```javascript
const apiUrl = import.meta.env.VITE_API_BASE_URL;
```

### Vite Configuration

See `vite.config.js`:
- Dev server port: 3000
- Proxy to backend: /api → http://localhost:8080
- React plugin enabled for JSX/TSX

## Component Lifecycle

### StarChartForm
1. User fills form and clicks submit
2. Form validation
3. API request to generate chart
4. On success: Add to observations, reset form, show success message
5. On error: Display error message
6. Loading state during submission

### StarChartDisplay
1. Component mounts
2. Checks context for current observation
3. Displays observation details and chart image
4. Handles image load errors gracefully

### ObservationLog
1. Component mounts: Fetch all observations
2. Display in table format
3. User can filter by location/target
4. Click to select observation (updates context)
5. Delete with confirmation

## State Flow

```
User Input (Form)
    ↓
StarChartForm Component
    ↓
starChartAPI.generateStarChart()
    ↓
ObservationContext.addObservation()
    ↓
StarChartDisplay Component (Updated)
ObservationLog Component (Updated)
```

## Error Handling

- API errors caught and displayed to user
- Form validation errors shown immediately
- Network errors handled gracefully
- Image load failures don't break UI
- Console logging for debugging

## Performance Optimization

- Component memoization (use React.memo for list items)
- Lazy loading of components (use React.lazy)
- CSS modules for scoped styling
- Debouncing for search filters
- Virtual scrolling for large lists (future enhancement)

## Browser Support

- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+

## Accessibility

- Semantic HTML elements
- Form labels associated with inputs
- ARIA attributes where needed
- Keyboard navigation support
- Color contrast compliance

## Testing

### Unit Tests
```bash
npm run test
```

### E2E Tests
```bash
npm run test:e2e
```

## Debugging

### Browser DevTools
- React DevTools extension recommended
- Network tab for API calls
- Console for error messages

### Code Debugging
- Add `debugger;` statements in code
- Use VS Code debugger with configuration
- Check browser console for errors

## Common Issues

### API Connection Failed
- Verify backend is running on port 8080
- Check CORS configuration
- Ensure API_BASE_URL is correct

### Form Not Submitting
- Check browser console for errors
- Verify form validation passes
- Check network tab for API response

### Styles Not Applying
- Clear browser cache
- Restart dev server
- Check CSS file paths

## Future Enhancements

- Add user authentication UI
- Google Maps integration for location picker
- Advanced filtering and sorting
- Export observations (PDF, CSV)
- Dark/light theme toggle
- Offline support with IndexedDB
- Progressive Web App (PWA) support
- Real-time notifications
- Mobile app version

## Development Tools

Recommended extensions for VS Code:
- ES7+ React/Redux/React-Native snippets
- Prettier - Code formatter
- ESLint
- Thunder Client or REST Client

## Contributing

1. Follow existing code style
2. Use functional components and hooks
3. Keep components focused and reusable
4. Document complex logic
5. Test across browsers/devices

## Support

For issues, refer to the main README.md in the project root.

