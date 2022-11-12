/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: 'class',
  content: ["./public/**/*.{html,js}"],
  theme: {
    
    colors: {
      "blue": "#0284c7",
      "white": "#f9fafb",
      "black": "#0f172a",
      "black-1": "#0f172a",
      "black-2": "#1e293b",
      "grey": "#334155",
      "grey-1": "#64748b",
      "grey-2": "#f1f5f9",
      "not-threatened": "#02a028",
      "naturally-uncommon": "#649a31",
      "relict": "#99cb68",
      "recovering": "#fecc33",
      "declining": "#fe9a01",
      "nationally-increasing": "#c26967",
      "nationally-vulnerable": "#9b0000",
      "nationally-endangered": "#660032",
      "nationally-critical": "#320033",
      "extinct": "#000000",
      "data-deficient": "#000000"
    },
   
    extend: {},
  },
  
  plugins: [],
}
