import { useEffect, useState } from 'react'
import { API_URL } from './config.js'

function formatTime(occurredAt) {
  return new Date(occurredAt).toLocaleTimeString([], {
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
  })
}

function EventLog({ day }) {
  const [events, setEvents] = useState([])

  useEffect(() => {
    fetch(`${API_URL}/api/events`)
      .then((response) => response.json())
      .then(setEvents)
  }, [day])

  if (events.length === 0) {
    return (
      <p className="empty-note">
        No events yet - once the simulation is running, activity like hunts and new agents will show up here.
      </p>
    )
  }

  return (
    <ul className="log-list">
      {events.map((event) => (
        <li className="log-item" key={event.id}>
          <span className="log-time">{formatTime(event.occurredAt)}</span>
          <span className="log-type">{event.eventType}</span>
          <span className="log-description">{event.description}</span>
        </li>
      ))}
    </ul>
  )
}

export default EventLog
