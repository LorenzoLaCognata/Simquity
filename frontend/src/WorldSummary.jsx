import { useEffect, useState } from 'react'
import { API_URL } from './config.js'

function formatLabel(key) {
  const withSpaces = key.replace(/([A-Z])/g, ' $1')
  return withSpaces.charAt(0).toUpperCase() + withSpaces.slice(1)
}

function WorldSummary() {
  const [counts, setCounts] = useState(null)

  useEffect(() => {
    fetch(`${API_URL}/api/world/summary`)
      .then((response) => response.json())
      .then(setCounts)
  }, [])

  if (!counts) {
    return <p className="empty-note">Loading world summary...</p>
  }

  return (
    <div className="summary-grid">
      {Object.entries(counts).map(([key, value]) => (
        <div className="summary-card" key={key}>
          <div className="count">{value}</div>
          <div className="label">{formatLabel(key)}</div>
        </div>
      ))}
    </div>
  )
}

export default WorldSummary