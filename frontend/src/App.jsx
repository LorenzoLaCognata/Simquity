import { useEffect, useState } from 'react'
import HistoryChart from './HistoryChart.jsx'
import { API_URL } from './config.js'

function App() {
  const [message, setMessage] = useState(null)
  const [error, setError] = useState(null)

  const [day, setDay] = useState(null)
  const [running, setRunning] = useState(false)

  useEffect(() => {
    fetch(`${API_URL}/api/hello`)
      .then((response) => response.json())
      .then((data) => setMessage(data.message))
      .catch(() => setError('Could not reach the backend. Is it running on port 8080?'))
  }, [])

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
    <div style={{ fontFamily: 'sans-serif', padding: '2rem' }}>
      <h1>Simquity</h1>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {!error && !message && <p>Waiting for the backend...</p>}
      {message && <p><strong>{message}</strong></p>}

      <hr style={{ margin: '2rem 0' }} />

      <p style={{ fontSize: '2rem', margin: '0.5rem 0' }}>
        Day {day === null ? '...' : day}
      </p>
      <p>Status: <strong>{running ? 'Running' : 'Stopped'}</strong></p>
      <button onClick={play} disabled={running} style={{ marginRight: '0.5rem' }}>
        Play
      </button>
      <button onClick={stop} disabled={!running}>
        Stop
      </button>

      <HistoryChart day={day} />
    </div>
  )
}

export default App
