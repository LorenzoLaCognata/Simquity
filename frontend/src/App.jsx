import { useEffect, useState } from 'react'
import { Play, Square, LayoutGrid, Map as MapIcon, LineChart, ScrollText } from 'lucide-react'
import HistoryChart from './HistoryChart.jsx'
import WorldSummary from './WorldSummary.jsx'
import WorldMap from './WorldMap.jsx'
import EventLog from './EventLog.jsx'
import { API_URL } from './config.js'
import './App.css'

const TABS = [
  { id: 'counts', label: 'Counts', icon: LayoutGrid },
  { id: 'map', label: 'Map', icon: MapIcon },
  { id: 'chart', label: 'Chart', icon: LineChart },
  { id: 'log', label: 'Log', icon: ScrollText },
]

function App() {
  const [day, setDay] = useState(null)
  const [running, setRunning] = useState(false)
  const [activeTab, setActiveTab] = useState('counts')

  useEffect(() => {
    fetch(`${API_URL}/api/simulation/state`)
      .then((response) => response.json())
      .then((data) => {
        setDay(data.day)
        setRunning(data.running)
      })

    const eventSource = new EventSource(`${API_URL}/api/simulation/stream`)
    eventSource.onmessage = (event) => {
      const data = JSON.parse(event.data)
      setDay(data.day)
      setRunning(data.running)
    }

    return () => eventSource.close()
  }, [])

  function play() {
    fetch(`${API_URL}/api/simulation/play`, { method: 'POST' })
  }

  function stop() {
    fetch(`${API_URL}/api/simulation/stop`, { method: 'POST' })
  }

  return (
    <div className="app">
      <header className="app-header">
        <h1>Simquity</h1>
        <div className="clock-bar">
          <button onClick={play} disabled={running} aria-label="Play">
            <Play size={16} />
          </button>
          <button onClick={stop} disabled={!running} aria-label="Stop">
            <Square size={16} />
          </button>
          <span className="day">Day {day === null ? '...' : day}</span>
          <span className="status">{running ? 'Running' : 'Stopped'}</span>
        </div>
      </header>

      <nav className="tab-bar">
        {TABS.map(({ id, label, icon: Icon }) => (
          <button
            key={id}
            className={activeTab === id ? 'tab active' : 'tab'}
            onClick={() => setActiveTab(id)}
          >
            <Icon size={16} />
            {label}
          </button>
        ))}
      </nav>

      <section>
        {activeTab === 'counts' && <WorldSummary />}
        {activeTab === 'map' && (
          <div className="chart-card">
            <WorldMap />
          </div>
        )}
        {activeTab === 'chart' && (
          <div className="chart-card">
            <HistoryChart day={day} />
          </div>
        )}
        {activeTab === 'log' && (
          <div className="chart-card">
            <EventLog day={day} />
          </div>
        )}
      </section>
    </div>
  )
}

export default App
