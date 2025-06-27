export const stringToJSON = (jsonString) => {
    const formattedJsonString = jsonString.replace(/\\+/g, '/');
    return JSON.parse(formattedJsonString);
}