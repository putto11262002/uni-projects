// DOM elements
let container = document.querySelector("#container");
let searchButton = document.querySelector("#search-button");
let sidebarToggleButtoon = document.querySelector("#sidebar-toggler");
let sidebar = document.querySelector("#sidebar");
let themeMenuToggleButton = document.querySelector("#theme-menu-toggle-button");
let toggleDarkTheme = document.querySelector("#toggle-dark-theme");
let toggleLightTheme = document.querySelector("#toggle-light-theme");
let themeMenu = document.querySelector("#theme-menu");
let body = document.querySelector("body");

//states
let showThemeMenu = false;
let showSidebar = false;
let theme = "light";
let birdData = [];

// constants
const BIRD_API = "./data/nzbird.json";
const IMAGE_ROOT_PATH = "./";
const STATUS_MAP = {
  "Not Threatened": "not-threatened",
  "Naturally Uncommon": "naturally-uncommon",
  Relict: "relict",
  Recovering: "recovering",
  Declining: "declining",
  "Nationally Increasing": "nationally-increasing",
  "Nationally Vulnerable": "nationally-vulnerable",
  "Nationally Endangered": "nationally-endangered",
  "Nationally Critical": "nationally-critical",
  Extinct: "extinct",
  "Data Deficient": "data-deficient",
};

const SORT_FUNCTIONS = {
  lightest_to_heaviest: (a, b) => a.size.weight.value - b.size.weight.value,
  heaviest_to_lightest: (a, b) => b.size.weight.value - a.size.weight.value,
  longest_to_shortest: (a, b) => b.size.length.value - a.size.length.value,
  shortest_to_longest: (a, b) => a.size.length.value - b.size.length.value,
  primary_name: (a, b) => a.primary_name.localeCompare(b.primary_name),
  english_name: (a, b) => a.english_name.localeCompare(b.english_name),
  scientific_name: (a, b) => a.scientific_name.localeCompare(b.scientific_name),
};

// Initialise the theme of the application
// Use theme in local storage otherwise use light theme
const initTheme = () => {
  theme = window.localStorage.getItem("theme");
  if (!theme) theme === "light";
  window.localStorage.setItem("theme", theme);
  let icon = document.createElement("i");
  if (theme === "dark") {
    body.className = "dark";
    icon.className = "bi bi-lightbulb text-white";
  } else {
    body.className = "";
    icon.className = "bi bi-moon text-white";
  }
  themeMenuToggleButton.appendChild(icon);
};
// Create bird card in the DOM from bird object
const createBirdCard = (bird) => {
  let cardContainer = document.createElement("div");
  cardContainer.className =
    "w-60 md:w-1/2 max-w-xs bg-white dark:bg-black-2 drop-shadow rounded-md relative hover:scale-105 hover:z-10 transition-transform ease-in-out delay-150 duration-300";

  let imageContainer = document.createElement("div");
  imageContainer.className = "h-60 w-full relative";

  let image = document.createElement("img");
  image.src = IMAGE_ROOT_PATH + bird.photo.source;
  image.className = "object-fill h-full w-full rounded-t-md";

  let imageOverlay = document.createElement("div");
  imageOverlay.className = "absolute bottom-0 left-0 z-10 py-3 px-5";

  let primaryName = document.createElement("h2");
  primaryName.innerText = bird.primary_name;
  primaryName.className = "text-lg font-semibold text-white leading-tight";

  let imageCredit = document.createElement("p");
  imageCredit.innerText = bird.photo.credit;
  imageCredit.className = "text-xs text-grey-2 leading-tight";

  imageOverlay.appendChild(primaryName);
  imageOverlay.appendChild(imageCredit);

  imageContainer.appendChild(image);
  imageContainer.appendChild(imageOverlay);

  let statusIcon = document.createElement("div");
  statusIcon.className = `h-12 w-12 rounded-full absolute z-20 border-white border-2 bg-${
    STATUS_MAP[bird.status]
  }`;
  statusIcon.style.top = "13.5rem";
  statusIcon.style.right = "1rem";

  let infoSection = document.createElement("section");
  infoSection.className = "px-5 py-3";

  let englishName = document.createElement("h2");
  englishName.innerText = bird.english_name;
  englishName.className = "font-semibold dark:text-white";

  infoSection.appendChild(englishName);
  infoSection.appendChild(
    createCardProperty("Scientific name", bird.scientific_name)
  );
  infoSection.appendChild(createCardProperty("Family", bird.family));
  infoSection.appendChild(createCardProperty("Order", bird.order));
  infoSection.appendChild(createCardProperty("Status", bird.status));
  infoSection.appendChild(
    createCardProperty(
      "Length",
      `${bird.size.length.value} ${bird.size.length.units}`
    )
  );
  infoSection.appendChild(
    createCardProperty(
      "Weight",
      `${bird.size.weight.value} ${bird.size.weight.units}`
    )
  );

  cardContainer.appendChild(statusIcon);

  cardContainer.appendChild(imageContainer);

  cardContainer.appendChild(infoSection);

  container.appendChild(cardContainer);
};

// create and return HTML element of the card property given the key and value of the property
const createCardProperty = (key, value) => {
  let propertyContainer = document.createElement("div");
  propertyContainer.className = "flex justify-start items-center py-1";
  let propertyKey = document.createElement("p");
  propertyKey.className =
    "inline-block text-xs font-semibold text-grey dark:text-grey-1  w-2/5";
  let propertyValue = document.createElement("p");
  propertyValue.className =
    "inline-block text-xs text-grey w-3/5 dark:text-grey-1";
  propertyKey.innerText = key;
  propertyValue.innerText = value;

  propertyContainer.appendChild(propertyKey);
  propertyContainer.appendChild(propertyValue);

  return propertyContainer;
};

// fetch bird data and store data in birdData
const fetchBirdData = async () => {
  try {
    let res = await fetch(BIRD_API);
    let data = await res.json();
    birdData = data;
  } catch (err) {
    console.error(err);
  }
};

// Renders list of bird cards in th DOM
const renderBirdCards = (birdData) => {
  container.innerHTML = "";

  if (birdData.length < 1) {
    let message = document.createElement("h1");
    message.innerText = "No data";
    return;
  }

  let numResult = document.querySelector("#num-results");
  numResult.innerText = birdData.length;

  birdData.forEach((bird) => {
    createBirdCard(bird);
  });
};

// apply search and filter to the birdData and rerender the list of the bird cards
const searchAndFilter = (e) => {
  e.preventDefault();
  let result = birdData;
  let statusFilter = document.querySelector("#filter-status").value;
  let sortBy = document.querySelector("#sort-by").value;
  let searchTerm = document.querySelector("#name-search").value;

  if (searchTerm !== undefined && searchTerm !== "") {
    searchTerm = searchTerm.toLowerCase();
    result = result.filter(
      (bird) =>
        bird.primary_name
          .normalize("NFD")
          .replace(/[\u0300-\u036f]/g, "")
          .toLowerCase()
          .includes(searchTerm) ||
        bird.english_name.toLowerCase().includes(searchTerm) ||
        bird.scientific_name.toLowerCase().includes(searchTerm)
    );
  }

  if (statusFilter !== "NO_FILTER") {
    result = result.filter((bird) => bird.status === statusFilter);
  }

  if (sortBy !== "NO_SORT") {
    result = result.sort(SORT_FUNCTIONS[sortBy]);
  }

  renderBirdCards(result);
};

const handleToggleSideBar = (e) => {
  if (showSidebar) {
    sidebar.style.display = "none";
    showSidebar = false;
  } else {
    sidebar.style.display = "inline";
    showSidebar = true;
  }
};

const handleToggleThemeMenu = (e) => {
  if (showThemeMenu) {
    themeMenu.style.display = "none";
    showThemeMenu = false;
  } else {
    themeMenu.style.display = "inline";
    showThemeMenu = true;
  }
};

const handleToggleLightTheme = (e) => {
  theme = "light";
  body.className = "";
  icon = document.createElement("i");
  icon.className = "bi bi-moon text-white";
  window.localStorage.setItem("theme", "light");
  themeMenuToggleButton.innerHTML = "";
  themeMenuToggleButton.appendChild(icon);

  handleToggleThemeMenu();
};

const handleToggleDarkTheme = (e) => {
  theme = "dark";
  body.className = "dark";
  icon = document.createElement("i");
  icon.className = "bi bi-lightbulb text-white";
  window.localStorage.setItem("theme", "dark");
  themeMenuToggleButton.innerHTML = "";
  themeMenuToggleButton.appendChild(icon);
  handleToggleThemeMenu();
};

const handleOnResize = (e) => {
  if (document.documentElement.clientWidth > 640) {
    if (!showSidebar) {
      sidebar.style.display = "inline";
      showSidebar = true;
    }
  }

  if (document.documentElement.clientWidth < 640) {
    if (showSidebar) {
      sidebar.style.display = "none";
      showSidebar = false;
    }
  }
};

const handleOnMouseup = (e) => {
  if (
    handleToggleSideBar &&
    e.target !== sidebar &&
    !sidebar.contains(e.target) &&
    document.documentElement.clientWidth < 640
  ) {
    sidebar.style.display = "none";
    showSidebar = false;
  }
};

// fetch bird data and render thelist of bird cards
const init = async () => {
  await fetchBirdData();
  renderBirdCards(birdData);
};

init();
initTheme();
searchButton.addEventListener("click", searchAndFilter);
sidebarToggleButtoon.addEventListener("click", handleToggleSideBar);
themeMenuToggleButton.addEventListener("click", handleToggleThemeMenu);
window.addEventListener("resize", handleOnResize);
document.addEventListener("mouseup", handleOnMouseup);
toggleDarkTheme.addEventListener("click", handleToggleDarkTheme);
toggleLightTheme.addEventListener("click", handleToggleLightTheme);
