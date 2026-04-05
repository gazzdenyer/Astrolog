import React from 'react';
import { ObservationProvider } from './context/ObservationContext';
import StarChartForm from './components/StarChartForm';
import StarChartDisplay from './components/StarChartDisplay';
import ObservationLog from './components/ObservationLog';
import './App.css';

function App() {
  return (
    <ObservationProvider>
      <div className="app-container">
        <header className="app-header">
          <div className="header-content">
            <h1>⭐ AstroLog</h1>
            <p>Star Chart Observation Logger</p>
          </div>
        </header>

        <main className="app-main">
          <div className="layout">
            <aside className="sidebar">
              <StarChartForm />
            </aside>

            <section className="main-content">
              <div className="display-section">
                <StarChartDisplay />
              </div>

              <div className="log-section">
                <ObservationLog />
              </div>
            </section>
          </div>
        </main>

        <footer className="app-footer">
          <p>AstroLog &copy; 2026 - Powered by AstronomyAPI</p>
        </footer>
      </div>
    </ObservationProvider>
  );
}

export default App;

