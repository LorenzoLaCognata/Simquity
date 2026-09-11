import { useEffect, useRef, useState } from 'react'
import Chart from 'chart.js/auto'
import { API_URL } from './config.js'

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

    if (chartInstance.current) {
      chartInstance.current.destroy()
    }

    chartInstance.current = new Chart(canvasRef.current, {
      type: 'line',
      data: {
        labels: history.map((point) =>
          new Date(point.recordedAt).toLocaleTimeString()
        ),
        datasets: [
          {
            label: 'Simulated day',
            data: history.map((point) => point.day),
            borderColor: '#2a78d6',
            backgroundColor: 'rgba(42,120,214,0.1)',
            fill: true,
            tension: 0.2,
            pointRadius: 2,
          },
        ],
      },
      options: {
        responsive: true,
        animation: false,
        scales: {
          y: { beginAtZero: true, title: { display: true, text: 'Day' } },
          x: { title: { display: true, text: 'Real time recorded' } },
        },
      },
    })
  }, [history])

  if (history.length === 0) {
    return <p>No history yet - click Play to start generating data.</p>
  }

  return <canvas ref={canvasRef} height="120"></canvas>
}

export default HistoryChart