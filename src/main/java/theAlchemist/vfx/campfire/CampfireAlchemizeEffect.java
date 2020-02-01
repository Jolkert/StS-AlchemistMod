package theAlchemist.vfx.campfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theAlchemist.ui.campfire.AlchemizeOption;

public class CampfireAlchemizeEffect extends AbstractGameEffect
{// Pretty much all of this code is straight up copy-pasted from com.megacrit.cardcrawl.vfx.campfire.CampfireDigEffect.class if you were wondering -Jolkert 2020-02-01
	private static final float DUR = 2.0F;
	private boolean hasAlchemized = false;
	private Color screenColor;
	
	public CampfireAlchemizeEffect()
	{
		this.screenColor = AbstractDungeon.fadeColor.cpy();
		this.duration = 2.0F;
		this.screenColor.a = 0.0F;
		((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
	}
	
	@Override
	public void update()
	{
		this.duration -= Gdx.graphics.getDeltaTime();
		this.updateBlackScreenColor();
		if (this.duration < 1.0F && !this.hasAlchemized)
		{
			this.hasAlchemized = true;
			CardCrawlGame.sound.play("POTION_2");
			AbstractDungeon.getCurrRoom().rewards.clear();
			
			for(int i = 0; i < AlchemizeOption.POTIONS_TO_RECEIVE; i++)
				AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(AbstractDungeon.returnRandomPotion()));
			
			AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
			AbstractDungeon.combatRewardScreen.open();
			CardCrawlGame.metricData.addCampfireChoiceData("ALCHEMIZE");
		}
		
		if (this.duration < 0.0F)
		{
			this.isDone = true;
			((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
			AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
		}
		
	}
	
	private void updateBlackScreenColor()
	{
		if (this.duration > 1.5F)
			this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.5F) * 2.0F);
		else if (this.duration < 1.0F)
			this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration);
		else
			this.screenColor.a = 1.0F;
		
	}
	
	@Override
	public void render(SpriteBatch spriteBatch)
	{
		spriteBatch.setColor(this.screenColor);
		spriteBatch.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
	}
	
	@Override
	public void dispose(){}
}
