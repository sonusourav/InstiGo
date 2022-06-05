# Commit Style Guidelines

### How to Write Good Commit Messages

A commit message consists of 3 parts:
- shortlog
- commit body
- issue reference

Example:
```
setup.py: Change Setup --> setup in filename

The file name was misleading.

Closes https://github.com/oss2019/information-security/issues/5861
```

Shortlog
Example:
```
Change Setup --> setup in filename
```
- Maximum of 50 characters.(Keeping subject lines at this length ensures that they are readable, and explains the change in a concise way.)
- Should describe the change - the action being done in the commit.
- Should not include WIP prefix.
- Should have a tag and a short description separated by a colon (:)

Commit Body
Example:
```
The file name was misleading.
```

- Maximum of 72 chars excluding newline for each line.(The recommendation is to add a line break at 72 characters, so that Git has plenty of room to indent text while still keeping everything under 80 characters overall.)
- Not mandatory - but helps explain what you’re doing.
- Should describe the reasoning for your changes. This is especially important for complex changes that are not self explanatory. This is also the right place to write about related bugs.
- First person should not be used here.

Issue reference
Example:

```
Closes https://github.com/oss2019/instigo-android/issues/61
```

- Should use the `Fixes` keyword if your commit fixes a bug, or `Closes` if it adds a feature/enhancement.
- In some situations, e.g. bugs overcome in documents, the difference between `Fixes` and `Closes` may be very small and subjective. If a specific issue may lead to an unintended behaviour from the user or from the program it should be considered a bug, and should be addresed with `Fixes`. If an issue is labelled with `type/bug` you should always use `Fixes`
- Should use full URL to the issue.
- There should be a single space between the `Fixes` or `Closes` and the URL.
