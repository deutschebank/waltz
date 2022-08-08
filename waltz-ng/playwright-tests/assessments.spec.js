import {test, expect} from '@playwright/test';
import _ from "lodash";
import {hoistSection, openSection, search, unHoistSection} from "./playwright-test-utils";

test.describe("assessments section", () => {

    test.beforeEach(async ({page}) => {
        // Go to the starting url before each test.
        await page.goto("/home");
        await search(page, "Test Application");
        const parentUrl = await page.url();
        expect(parentUrl).toContain('application');
        await openSection(page, "Assessment Ratings");
        await hoistSection(page, "assessment-rating-section");
    });

    test('Can toggle assessment group list', async ({page}) => {
        const assessmentGroup = await page.locator(".assessment-group:has-text('Uncategorized')");
        const toggle = await assessmentGroup.locator(".assessment-group-header button");
        const icon = await toggle.locator(".icon");
        await expect(icon).toHaveAttribute("data-ux", "caret-down");
        await toggle.click();
        await expect(icon).toHaveAttribute("data-ux", "caret-right");
        await toggle.click();
        await expect(icon).toHaveAttribute("data-ux", "caret-down");
    });

    test('Can favourite assessment and shows in overview', async ({context, page}) => {

        const assessmentGroup = await page.locator(".assessment-group:has-text('Uncategorized')");
        const assessment = await assessmentGroup.locator("tr:has-text('Test Definition B')");
        const favouritesButton = await assessment.locator("button", {has: page.locator(".icon")});
        await favouritesButton.click();
        const icon = await favouritesButton.locator(".icon");
        await expect(icon).toHaveAttribute("data-ux", "star");

        const [newPage] = await Promise.all([
            context.waitForEvent('page'),
            unHoistSection(context, page)
        ])

        const parentUrl = await newPage.url();
        expect(parentUrl).not.toContain('embed');
        expect(parentUrl).toMatch(/application/i);

        const assessmentsSection = await newPage.locator("waltz-assessment-rating-sub-section");
        const assessmentInOverview = assessmentsSection.locator("table tr td:has-text('Test Definition B')");
        await expect(assessmentInOverview).toHaveCount(1);
    });

    test('Can un-favourite assessment and removed from overview', async ({context, page}) => {

        const assessmentGroup = await page.locator(".assessment-group:has-text('Uncategorized')");
        const assessment = await assessmentGroup.locator("tr:has-text('Test Definition A')");
        const favouritesButton = await assessment.locator("button", {has: page.locator(".icon")});
        await favouritesButton.click();
        const icon = await favouritesButton.locator(".icon");
        await expect(icon).toHaveAttribute("data-ux", "star-o");

        const [newPage] = await Promise.all([
            context.waitForEvent('page'),
            unHoistSection(context, page)
        ])

        const parentUrl = await newPage.url();
        expect(parentUrl).not.toContain('embed');
        expect(parentUrl).toMatch(/application/i);

        const assessmentsSection = await newPage.locator("waltz-assessment-rating-sub-section");
        const assessmentInOverview = assessmentsSection.locator("table tr td:has-text('Test Definition A')");
        await newPage.pause();
        await expect(assessmentInOverview).toHaveCount(0);
    });

});
