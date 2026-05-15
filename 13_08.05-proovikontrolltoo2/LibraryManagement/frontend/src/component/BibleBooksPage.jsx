import React, { useEffect, useMemo, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./BibleBooksPage.css";

const API_URL = "http://localhost:8081/api/bibles";

function BibleBooksPage() {
  const [bibles, setBibles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchBibles() {
      try {
        setLoading(true);
        setError("");

        const response = await fetch(API_URL);
        if (!response.ok) {
          throw new Error("Server returned an error");
        }

        const data = await response.json();
        setBibles(Array.isArray(data) ? data : []);
      } catch (e) {
        setError("Raamatute laadimine ebaonnestus. Proovi hiljem uuesti.");
      } finally {
        setLoading(false);
      }
    }

    fetchBibles();
  }, []);

  const normalizedBibles = useMemo(() => {
    return bibles.map((item, index) => ({
      id: item?.id ?? item?.abbreviation ?? item?.name ?? index,
      name: item?.name ?? item?.title ?? "Nimetu",
      abbreviation: item?.abbreviation ?? item?.abbr ?? "-",
      language: item?.language ?? item?.lang ?? "-",
      version: item?.version ?? item?.translation ?? "-",
      source: item?.source ?? "Holy Bible API"
    }));
  }, [bibles]);

  return (
    <div className="bible-page">
      <div className="container py-4">
        <h2 className="bible-page-title">Bible Catalog (through backend API)</h2>

        {loading && <div className="alert alert-info">Laen raamatuid...</div>}

        {error && <div className="alert alert-danger">{error}</div>}

        {!loading && !error && normalizedBibles.length === 0 && (
          <div className="alert alert-warning">Raamatuid ei leitud.</div>
        )}

        <div className="row g-3">
          {normalizedBibles.map((bible) => (
            <div className="col-12 col-sm-6 col-lg-4" key={bible.id}>
              <div className="card bible-card h-100 shadow-sm border-0">
                <div className="card-body">
                  <h5 className="card-title mb-3">{bible.name}</h5>
                  <p className="card-text mb-1"><strong>Abbreviation:</strong> {bible.abbreviation}</p>
                  <p className="card-text mb-1"><strong>Language:</strong> {bible.language}</p>
                  <p className="card-text mb-1"><strong>Version:</strong> {bible.version}</p>
                  <p className="card-text text-muted mt-3 mb-0">Source: {bible.source}</p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default BibleBooksPage;
