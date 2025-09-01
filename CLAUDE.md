# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Overview
This is a test repository designed for Claude Code subagent experimentation. Despite the Java-oriented name "test-calc-java-subagent-a", the repository is currently configured as a JavaScript/React project with comprehensive Claude Code agent configurations.

## Development Commands
Based on the Claude settings configuration:
- **Build**: `npm run build`
- **Development server**: `npm run dev` 
- **Testing**: `npm test`
- **Linting**: `npm run lint`
- **Package management**: npm (Node.js 18.x)

## Specialized Agents Available
This repository includes several specialized Claude Code agents:
- **developer**: TDD-focused development with strict test-first practices and detailed PR management
- **test-developer**: Alternative TDD development agent
- **design-expert**: Design guidance and analysis
- **manager-agent**: Project management and coordination
- **manager-doc**: Documentation generation and design specification verification
- **manager-pj**: Project planning and timeline management
- **analyzer-pj**: Comprehensive project analysis for design decisions
- **review-cq**: Code quality-focused expert code review

## Custom Commands
- `/pr`: Creates PRs using the manager-agent with gh command
- `/req`: Coordinates with various agents using the manager-agent

## Code Style & Standards
- Indentation: 2 spaces
- Semicolons: required
- Quotes: single quotes preferred
- Trailing commas: enabled
- Max line length: 100 characters

## Git Workflow
- Protected branches: main, master, production
- Pull requests required for main branches
- Commit message template: `[type]: brief description` with detailed changes
- Auto-staging disabled - manual staging required

## Security & Quality
- Secret commit prevention enabled
- Dependency scanning enabled
- Code review required
- Test coverage expectations maintained by TDD agents