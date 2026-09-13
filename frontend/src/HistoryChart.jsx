import { useEffect, useRef, useState } from 'react'
import Chart from 'chart.js/auto'
import { API_URL } from './config.js'

const HOURS_IN_WINDOW = 7 * 24

function buildHourlyBuckets(history) {
  const now = Date.now()
  const hourMs = 60 * 60 * 1000
  const sorted = [...history].sort(
    (a, b) => new Date(a.recordedAt) - new Date(b.recordedAt)
  )

  const labels = []
  const data = []
  let historyIndex = 0
  let lastKnownDay = 0

  for (let hoursAgo = HOURS_IN_WINDOW; hoursAgo >= 0; hoursAgo--) {
    const bucketTime = now - hoursAgo * hourMs
    while (
      historyIndex < sorted.length &&
      new Date(sorted[historyIndex].recordedAt).getTime() <= bucketTime
    ) {
      lastKnownDay = sorted[historyIndex].day
      historyIndex++
    }
    labels.push(
      new Date(bucketTime).toLocaleString([], { weekday: 'short', hour: '2-digit' })
    )
    data.push(lastKnownDay)
  }

  return { labels, data }
}

function HistoryChart({ day }) {
  const canvasRef = useRef(null)
  const chartInstance = useRef(null)
  const [history, setHistory] = useState([])

  useEffect(() => {
    fetch(`${API_URL}/api/simulation/history`)
      .then((response) => response.json())
      .then(setHistory)
  }, [day])

  useEffect(() => {
    if (!canvasRef.current) return
    if (chartInstance.current) chartInstance.current.destroy()

    const { labels, data } = buildHourlyBuckets(history)

    chartInstance.current = new Chart(canvasRef.current, {
      type: 'line',
      data: {
        labels,
        datasets: [
          {
            data,
            borderColor: '#2a5db0',
            backgroundColor: 'rgba(42, 93, 176, 0.08)',
            fill: true,
            tension: 0.15,
            borderWidth: 2,
            pointRadius: 0,
            pointHoverRadius: 3,
            pointBackgroundColor: '#2a5db0',
          },
        ],
      },
      options: {
        responsive: true,
        animation: false,
        plugins: { legend: { display: false } },
        scales: {
          y: {
            beginAtZero: true,
            grid: { color: '#e3e1da' },
            ticks: { color: '#7a7870' },
          },
          x: {
            grid: { display: false },
            ticks: { color: '#7a7870', maxTicksLimit: 7 },
          },
        },
      },
    })
  }, [history])

  return <canvas ref={canvasRef} height="140"></canvas>
}

export default HistoryChart
