# 📋 AstroLog TODO - Map Location Selector Feature

**Date Created**: April 5, 2026
**Priority**: Medium
**Status**: 🎯 Planned
**Complexity**: Medium

---

## Feature: Auto-Populate Location via Map Interface

Allow users to select their observation location visually on an interactive map, automatically populating the Location name, Latitude, and Longitude fields in the star chart form.

---

## 🎯 Feature Overview

### Current Flow
1. User manually types location name
2. User manually enters latitude
3. User manually enters longitude
4. User submits form

### Proposed Flow
1. User clicks "Select Location on Map" button
2. Interactive map opens
3. User clicks on their desired location
4. Location name, latitude, and longitude auto-populate
5. User can adjust if needed
6. User submits form

---

## 📋 Implementation Checklist

### Phase 1: Map Library Integration
- [ ] Install Leaflet.js or Mapbox GL JS
  ```bash
  npm install leaflet leaflet-react
  # or
  npm install mapbox-gl
  ```
- [ ] Add map styling CSS
- [ ] Configure map API keys (if required)
- [ ] Test basic map rendering

### Phase 2: Create MapSelector Component
- [ ] Create new component: `frontend/src/components/MapSelector.jsx`
- [ ] Implement map container
- [ ] Add click handler for map
- [ ] Display selected coordinates
- [ ] Add "Confirm" and "Cancel" buttons
- [ ] Style component to match AstroLog theme

### Phase 3: Integrate with StarChartForm
- [ ] Add "Select Location on Map" button to form
- [ ] Create modal/popup for MapSelector
- [ ] Pass selected location data back to form
- [ ] Auto-populate location, latitude, longitude fields
- [ ] Add validation for selected location

### Phase 4: Reverse Geocoding (Optional but Recommended)
- [ ] Integrate Nominatim API or Google Places API
- [ ] Get location name from coordinates
- [ ] Display meaningful location names instead of just coordinates
- [ ] Handle API errors gracefully

### Phase 5: User Experience Improvements
- [ ] Add ability to search locations by name
- [ ] Add current location button (geolocation API)
- [ ] Add zoom controls and markers
- [ ] Show selected location with visual marker
- [ ] Allow dragging marker to refine location

### Phase 6: Testing & Polish
- [ ] Unit tests for MapSelector component
- [ ] Integration tests with StarChartForm
- [ ] Mobile responsiveness testing
- [ ] Error handling and edge cases
- [ ] Performance optimization

---

## 🛠️ Technical Details

### Option 1: Leaflet.js (Recommended - Open Source)

**Pros:**
- Free and open-source
- Lightweight
- Great for custom implementations
- No API key required for basic functionality
- React library available (react-leaflet)

**Cons:**
- Base maps from various providers
- May require tile layer provider

**Implementation:**
```jsx
import { MapContainer, TileLayer, Marker, Popup, useMapEvents } from 'react-leaflet'

function MapSelector({ onLocationSelect }) {
  // Map logic here
}
```

### Option 2: Mapbox GL JS

**Pros:**
- Beautiful maps
- Advanced features
- Good performance
- Excellent documentation

**Cons:**
- Requires API key
- Pricing based on usage
- More complex setup

### Option 3: Google Maps API

**Pros:**
- Industry standard
- Extensive features
- Good address autocomplete

**Cons:**
- Requires API key
- Pricing can be high
- Requires authentication

---

## 📂 Files to Create/Modify

### New Files
```
frontend/src/components/
├── MapSelector.jsx          (New - Map interface component)
├── MapSelector.css          (New - Map styling)
└── LocationSearch.jsx       (New - Location search component - optional)

frontend/src/hooks/
└── useGeolocation.js        (New - Geolocation hook)
```

### Modified Files
```
frontend/src/components/
├── StarChartForm.jsx        (Add map button and modal)
└── StarChartForm.css        (Update styles)

frontend/src/styles/
└── MapSelector.css          (Map-specific styles)

frontend/package.json        (Add map dependencies)
```

---

## 🎨 UI/UX Specifications

### MapSelector Component Layout
```
┌─────────────────────────────────────┐
│  Select Observation Location        │
├─────────────────────────────────────┤
│                                     │
│    [Interactive Map Container]      │
│    (Click to select location)        │
│                                     │
│    Selected: Lat: X, Lon: Y         │
│                                     │
│  [Search Box] [Current Location]    │
│                                     │
├─────────────────────────────────────┤
│  [Cancel]              [Confirm]    │
└─────────────────────────────────────┘
```

### Integration with StarChartForm
```
StarChartForm Layout:
┌─────────────────────────────────────┐
│  Location: [input] [📍 Map Button]  │
│  Latitude: [input]                  │
│  Longitude: [input]                 │
│  ...other fields...                 │
└─────────────────────────────────────┘

When user clicks [📍 Map Button]:
- MapSelector modal opens
- User selects location
- Fields auto-populate
```

---

## 🔧 Implementation Steps

### Step 1: Install Dependencies
```bash
cd frontend
npm install leaflet react-leaflet
npm install axios  # for reverse geocoding API calls
```

### Step 2: Create MapSelector Component
```jsx
// MapSelector.jsx
import React, { useState } from 'react'
import { MapContainer, TileLayer, Marker, Popup, useMapEvents } from 'react-leaflet'
import './MapSelector.css'

export default function MapSelector({ onLocationSelect, onCancel }) {
  const [selectedLocation, setSelectedLocation] = useState(null)
  
  // Implementation here
}
```

### Step 3: Integrate with StarChartForm
```jsx
// Add to StarChartForm.jsx
const [showMapSelector, setShowMapSelector] = useState(false)

const handleMapSelect = (location) => {
  setFormData({
    ...formData,
    location: location.name,
    latitude: location.latitude,
    longitude: location.longitude
  })
  setShowMapSelector(false)
}

// Add button to form
<button onClick={() => setShowMapSelector(true)}>📍 Select on Map</button>

// Add modal
{showMapSelector && (
  <MapSelector 
    onLocationSelect={handleMapSelect}
    onCancel={() => setShowMapSelector(false)}
  />
)}
```

### Step 4: Add Reverse Geocoding (Optional)
```javascript
// Service to get location name from coordinates
async function getLocationName(lat, lon) {
  const response = await fetch(
    `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lon}`
  )
  const data = await response.json()
  return data.address.city || data.address.town || data.name
}
```

### Step 5: Test Integration
- Test clicking map button
- Test location selection
- Test field auto-population
- Test validation
- Test mobile responsiveness

---

## 🚀 Advanced Features (Future)

- [ ] Location history (recent selections)
- [ ] Favorite locations
- [ ] Address autocomplete
- [ ] Multiple map providers
- [ ] Draw observation area on map
- [ ] View past observations on map
- [ ] Share location via URL

---

## 📊 Estimated Effort

| Task | Effort | Time |
|------|--------|------|
| Map library setup | Low | 30 min |
| MapSelector component | Medium | 2 hours |
| StarChartForm integration | Low | 1 hour |
| Reverse geocoding | Medium | 1.5 hours |
| Testing & polish | Medium | 2 hours |
| Documentation | Low | 30 min |
| **Total** | **Medium** | **~7 hours** |

---

## 🧪 Testing Checklist

- [ ] Map loads correctly
- [ ] Click selects location
- [ ] Coordinates display accurately
- [ ] Location name populates
- [ ] Form fields update
- [ ] Works on mobile
- [ ] Error handling works
- [ ] Cancel button works
- [ ] Validation still works
- [ ] Performance is acceptable

---

## 📚 Resources

### Leaflet Documentation
- https://leafletjs.com/
- https://react-leaflet.js.org/

### Map Providers
- **OpenStreetMap (Free)**: https://www.openstreetmap.org/
- **Mapbox (Free tier)**: https://www.mapbox.com/
- **Google Maps**: https://developers.google.com/maps

### Reverse Geocoding
- **Nominatim (Free)**: https://nominatim.org/
- **Google Geocoding API**: https://developers.google.com/maps/documentation/geocoding

### React Geolocation
- https://developer.mozilla.org/en-US/docs/Web/API/Geolocation_API

---

## 🎯 Success Criteria

✅ User can open interactive map from form
✅ User can select location on map
✅ Location name, latitude, longitude auto-populate
✅ Fields are accurate (within reasonable precision)
✅ Works on desktop and mobile
✅ No performance degradation
✅ Graceful error handling
✅ Clean, professional UI

---

## 📝 Notes

- Consider using OpenStreetMap + Leaflet for free, open-source solution
- Nominatim can provide location names for free (with usage limits)
- React-leaflet simplifies integration with React components
- Geolocation API can provide user's current location as starting point
- Consider caching selected locations for quick reuse

---

## 🔐 Privacy & Security

- User location selection is local (no tracking)
- Coordinates only sent to backend when form submitted
- Respect user privacy preferences
- No unnecessary API calls
- Secure API key management (if using paid services)

---

## 📋 Current Status

**Phase**: Planning
**Next Step**: Install Leaflet and create MapSelector component
**Assigned To**: Open for development
**Dependencies**: None (self-contained feature)

---

## Related Issues

- [Frontend enhancement] Improve location input UX
- [User request] Make it easier to select coordinates

---

## Questions & Decisions

- [ ] Which map library to use? (Recommendation: Leaflet)
- [ ] Need reverse geocoding? (Recommendation: Yes - better UX)
- [ ] Support multiple map providers? (Recommendation: Start with one)
- [ ] Mobile-first or desktop-first? (Recommendation: Both equally)
- [ ] API key management strategy? (Recommendation: Environment variables)

---

**Created**: April 5, 2026
**Last Updated**: April 5, 2026
**Status**: 🎯 Ready for Development

