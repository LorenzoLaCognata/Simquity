import { useEffect, useState } from 'react'
import HistoryChart from './HistoryChart.jsx'
import WorldSummary from './WorldSummary.jsx'
import { API_URL } from './config.js'
import './App.css'

function App() {
  const [day, setDay] = useState(null)
  const [running, setRunning] = useState(false)

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
          <button onClick={play} disabled={running}>Play</button>
          <button onClick={stop} disabled={!running}>Stop</button>
          <span className="day">Day {day === null ? '...' : day}</span>
          <span className="status">{running ? 'Running' : 'Stopped'}</span>
        </div>
      </header>

      <section>
        <h2>World at a glance</h2>
        <WorldSummary />
      </section>

      <section>
        <h2>Day over time</h2>
        <div className="chart-card">
          <HistoryChart day={day} />
        </div>
      </section>
    </div>
  )
}

export default App
