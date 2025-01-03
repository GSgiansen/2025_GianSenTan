import { useState } from "react";
import "./App.css";

function App() {
  const coins = [0.01, 0.05, 0.1, 0.2, 0.5, 1, 2, 5, 10, 50, 100, 1000];

  const [amount, setAmount] = useState(0);

  const handleAmountChange = (e) => {
    setAmount(e.target.value);
  };

  const [selectedCoins, setSelectedCoins] = useState([]);

  return (
    <>
      <div>
        <h1>Welcome to coin change!</h1>
        <p>Click the following coins you wish to use</p>
        <div className="coin-buttons">
          {coins.map((coin, index) => (
            <button
      
              className={selectedCoins.includes(coin) ? "selected" : ""}
              key={index}
              onClick={() => {
                console.log(selectedCoins);
                if (selectedCoins.includes(coin)) {
                  setSelectedCoins(
                    selectedCoins.filter(
                      (selectedCoin) => selectedCoin !== coin
                    )
                  );
                } else {
                  setSelectedCoins([...selectedCoins, coin]);
                }
              }}
            >
              {coin}
            </button>
          ))}
        </div>
        <p>Enter an amount </p>
        <label htmlFor="amount">Enter Amount:</label>
        <input
          id="amount"
          value={amount}
          onChange={handleAmountChange}
          placeholder="Enter amount"
        />
      </div>

      <div>
        <button>Submit your test!</button>
      </div>
    </>
  );
}

export default App;
